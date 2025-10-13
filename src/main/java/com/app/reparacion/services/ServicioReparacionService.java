package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.enums.EstadoServicio;
import com.app.reparacion.repositories.ServicioReparacionRepository;

@Service
public class ServicioReparacionService {

    private final ServicioReparacionRepository servicioRepo;

    public ServicioReparacionService(ServicioReparacionRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    /** Crear servicio a partir de una oferta aceptada */
    public ServicioReparacion crearDesdeOferta(Oferta oferta) {
        ServicioReparacion servicio = new ServicioReparacion();
        servicio.setOferta(oferta);
        servicio.setFechaInicio(LocalDate.now());
        servicio.setEstado(EstadoServicio.EN_CURSO);
        return servicioRepo.save(servicio);
    }

    /** Finalizar un servicio */
    public ServicioReparacion finalizarServicio(Integer id) {
        ServicioReparacion servicio = servicioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setEstado(EstadoServicio.FINALIZADO);
        servicio.setFechaFin(LocalDate.now());
        return servicioRepo.save(servicio);
    }

    /** Cancelar un servicio */
    public ServicioReparacion cancelarServicio(Integer id) {
        ServicioReparacion servicio = servicioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setEstado(EstadoServicio.CANCELADO);
        servicio.setFechaFin(LocalDate.now());
        return servicioRepo.save(servicio);
    }

    public List<ServicioReparacion> listarServicios() {
        return servicioRepo.findAll();
    }

    public Optional<ServicioReparacion> obtenerPorId(Integer id) {
        return servicioRepo.findById(id);
    }


}
