package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.reparacion.models.SolicitudReparacion;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.repositories.SolicitudReparacionRepository;
import jakarta.transaction.Transactional;

@Service
public class SolicitudReparacionService {

    private final SolicitudReparacionRepository solicitudRepo; //Instancia de interfaz SolicitudReparacionRepository para hacer consultas CRUD a la DB

    public SolicitudReparacionService(SolicitudReparacionRepository solicitudRepo) {
        this.solicitudRepo = solicitudRepo;
    }

    /** Crear una nueva solicitud */
    @Transactional
public SolicitudReparacion crearSolicitud(SolicitudReparacion solicitud) {
    if (solicitud.getCliente() == null || solicitud.getCategoria() == null) {
        throw new IllegalArgumentException("La solicitud debe incluir cliente y categoría");
    }

    solicitud.setFechaSolicitud(LocalDate.now());
    solicitud.setEstado(Estado.PENDIENTE);

    return solicitudRepo.save(solicitud);
}

     /** Listar todas las solicitudes */
    public List<SolicitudReparacion> listarSolicitudes() {
        return solicitudRepo.findAll();
    }

    /** Buscar por ID */
    public Optional<SolicitudReparacion> obtenerPorId(Integer id) {
        return solicitudRepo.findById(id);
    }

    /** Cambiar estado */
    public SolicitudReparacion actualizarEstado(Integer id, Estado nuevoEstado) {
        SolicitudReparacion solicitud = solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        solicitud.setEstado(nuevoEstado);
        return solicitudRepo.save(solicitud);
    }

    /** Listar solicitudes de un cliente específico */
    public List<SolicitudReparacion> listarPorCliente(Integer idCliente) {
        return solicitudRepo.findByClienteIdUsuario(idCliente);
    }

    /** Eliminar una solicitud por ID */
    @Transactional
    public void eliminarSolicitud(Integer id) {
        SolicitudReparacion solicitud = solicitudRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    //Restringir qué solicitudes se pueden eliminar
    if (solicitud.getEstado() != Estado.PENDIENTE) {
        throw new IllegalArgumentException("Solo se pueden eliminar solicitudes pendientes");
    }

    solicitudRepo.delete(solicitud);
}
}
