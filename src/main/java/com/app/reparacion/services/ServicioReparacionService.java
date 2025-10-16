package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.reparacion.dto.ServicioResumenDTO;
import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.enums.EstadoServicio;
import com.app.reparacion.repositories.ServicioReparacionRepository;
import jakarta.transaction.Transactional;

@Service
public class ServicioReparacionService {

    private final ServicioReparacionRepository servicioRepo;

    public ServicioReparacionService(ServicioReparacionRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    /** Crear servicio a partir de una oferta aceptada */
    public ServicioReparacion crearServicio(Oferta oferta) {
        ServicioReparacion servicio = new ServicioReparacion();
        servicio.setOferta(oferta);
        servicio.setFechaInicio(LocalDate.now());
        servicio.setEstado(EstadoServicio.EN_CURSO);
        return servicioRepo.save(servicio);
    }

    /** Finalizar un servicio */
    @Transactional
    public ServicioReparacion finalizarServicio(Integer id) {
        ServicioReparacion servicio = servicioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setEstado(EstadoServicio.FINALIZADO);
        servicio.setFechaFin(LocalDate.now());
        return servicioRepo.save(servicio);
        //Agregar verificacion de estado antes del cierre para evitar doble cierre
    }

    /** Cancelar un servicio */
    @Transactional
    public ServicioReparacion cancelarServicio(Integer id) {
        ServicioReparacion servicio = servicioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setEstado(EstadoServicio.CANCELADO);
        servicio.setFechaFin(LocalDate.now());
        return servicioRepo.save(servicio);
        //Agregar verificacion de estado antes del cierre para evitar doble cierre
    }

    /** Historial (DTO optimizado) para un técnico */
    public List<ServicioResumenDTO> listarHistorialPorTecnico(Integer idTecnico) {
        return servicioRepo.listarHistorialServicios(idTecnico);
    }

    /** Cambiar estado del servicio con reglas básicas */
    @Transactional
    public ServicioReparacion actualizarEstado(Integer id, String nuevoEstado) {
        ServicioReparacion servicio = servicioRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        EstadoServicio estadoParseado = parseEstadoServicio(nuevoEstado);

        // si se finaliza o cancela, setear fecha fin
        if (estadoParseado == EstadoServicio.FINALIZADO || estadoParseado == EstadoServicio.CANCELADO) {
            servicio.setFechaFin(LocalDate.now());
        }

        servicio.setEstado(estadoParseado);
        return servicioRepo.save(servicio);
    }

    /** Helper: parsea string a enum de forma segura */
    private EstadoServicio parseEstadoServicio(String valor) {
        try {
            return EstadoServicio.valueOf(valor.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Estado inválido: " + valor + ". Valores permitidos: EN_CURSO, FINALIZADO, CANCELADO");
        }
    }

    public List<ServicioReparacion> listarServicios() {
        return servicioRepo.findAll();
    }

    public Optional<ServicioReparacion> obtenerPorId(Integer id) {
        return servicioRepo.findById(id);
    }
}
