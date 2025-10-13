package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.reparacion.dto.SolicitudResumenDTO;
import com.app.reparacion.models.Categoria;
import com.app.reparacion.models.Cliente;
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
    public SolicitudReparacion crearSolicitud(SolicitudReparacion solicitud, Cliente cliente, Categoria categoria) {
        solicitud.setCliente(cliente);
        solicitud.setCategoria(categoria);
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
        return solicitudRepo.findByClienteId(idCliente);
    }

    public List<SolicitudResumenDTO> obtenerSolicitudesPorCliente(Integer idCliente) {
        return solicitudRepo.listarPorCliente(idCliente);
    }
}
