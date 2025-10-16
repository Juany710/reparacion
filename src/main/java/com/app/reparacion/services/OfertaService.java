package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.app.reparacion.dto.OfertaResumenDTO;
import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.models.enums.EstadoServicio;
import com.app.reparacion.repositories.OfertaRepository;
import com.app.reparacion.repositories.ServicioReparacionRepository;

import jakarta.transaction.Transactional;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepo;
    private ServicioReparacionRepository servicioRepo;

    public OfertaService(OfertaRepository ofertaRepo, ServicioReparacionRepository servicioRepo) {
        this.ofertaRepo = ofertaRepo;
        this.servicioRepo = servicioRepo;
    }

      /** Crear una nueva oferta */
    @Transactional
    public Oferta crearOferta(Oferta oferta) {
    if (oferta.getSolicitud() == null || oferta.getTecnico() == null || oferta.getModalidad() == null) {
        throw new IllegalArgumentException("La oferta debe incluir solicitud, técnico y modalidad");
    }

    oferta.setFecha(LocalDate.now());
    oferta.setEstado(Estado.PENDIENTE);

    return ofertaRepo.save(oferta);
}

     /** Aceptar una oferta y generar el servicio correspondiente */
    @Transactional
    public ServicioReparacion aceptarOferta(Integer idOferta) {
        //Buscar la oferta seleccionada
        Oferta oferta = ofertaRepo.findById(idOferta)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        //Cambiar su estado a ACEPTADA
        oferta.setEstado(Estado.ACEPTADA);
        ofertaRepo.save(oferta);

        //Rechazar todas las demás ofertas de la misma solicitud
        ofertaRepo.rechazarOtras(
                oferta.getSolicitud(). getIdSolicitudReparacion(),
                oferta.getIdOferta()
        );

        //Crear el nuevo servicio de reparación
        ServicioReparacion servicio = new ServicioReparacion();
        servicio.setOferta(oferta);
        servicio.setEstado(EstadoServicio.EN_CURSO);

        //Guardar y devolver el servicio creado
        return servicioRepo.save(servicio);
    }

    /** Rechazar una oferta */
    public Oferta rechazarOferta(Integer id) {
        Oferta oferta = ofertaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
        oferta.setEstado(Estado.RECHAZADA);
        return ofertaRepo.save(oferta);
    }

     /** Listar todas las ofertas de una solicitud */
    public List<Oferta> listarPorSolicitud(Integer solicitudId) {
        return ofertaRepo.findBySolicitudId(solicitudId);
    }

    public List<OfertaResumenDTO> obtenerOfertasPorTecnico(Integer idTecnico) {
        return ofertaRepo.listarPorTecnico(idTecnico);
    }

    public Optional<Oferta> obtenerPorId(Integer id) {
    return ofertaRepo.findById(id);
}
}
