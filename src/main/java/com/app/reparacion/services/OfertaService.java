package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.app.reparacion.dto.OfertaResumenDTO;
import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.SolicitudReparacion;
import com.app.reparacion.models.Tecnico;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.models.enums.EstadoServicio;
import com.app.reparacion.models.enums.Modalidad;
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
    public Oferta crearOferta(Oferta oferta, SolicitudReparacion solicitud, Tecnico tecnico, Modalidad modalidad) {
        oferta.setSolicitud(solicitud);
        oferta.setTecnico(tecnico);
        oferta.setFecha(LocalDate.now());
        oferta.setEstado(Estado.PENDIENTE);
        oferta.setModalidad(modalidad);
        return ofertaRepo.save(oferta);
    }

     /** Aceptar una oferta y generar el servicio correspondiente */
    @Transactional
    public ServicioReparacion aceptarOferta(Integer idOferta) {
        // 1️⃣ Buscar la oferta seleccionada
        Oferta oferta = ofertaRepo.findById(idOferta)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        // 2️⃣ Cambiar su estado a ACEPTADA
        oferta.setEstado(Estado.ACEPTADA);
        ofertaRepo.save(oferta);

        // 3️⃣ Rechazar todas las demás ofertas de la misma solicitud
        ofertaRepo.rechazarOtras(
                oferta.getSolicitud(). getIdSolicitudReparacion(),
                oferta.getIdOferta()
        );

        // 4️⃣ Crear el nuevo servicio de reparación
        ServicioReparacion servicio = new ServicioReparacion();
        servicio.setOferta(oferta);
        servicio.setEstado(EstadoServicio.EN_CURSO);

        // 5️⃣ Guardar y devolver el servicio creado
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
}
