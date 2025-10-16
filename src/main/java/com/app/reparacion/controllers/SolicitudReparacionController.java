package com.app.reparacion.controllers;

import com.app.reparacion.models.SolicitudReparacion;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.services.SolicitudReparacionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudReparacionController {

    private final SolicitudReparacionService solicitudService;

    public SolicitudReparacionController(SolicitudReparacionService solicitudService) {
        this.solicitudService = solicitudService;
    }

    /** 🔹 Crear una nueva solicitud (cliente envía su requerimiento) */
    @PostMapping
    @Transactional
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudReparacion solicitud) {
        try {
            SolicitudReparacion nueva = solicitudService.crearSolicitud(solicitud);
            return ResponseEntity.ok(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 🔹 Obtener una solicitud por ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return solicitudService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** 🔹 Listar todas las solicitudes (solo admin o soporte) */
    @GetMapping
    public ResponseEntity<List<SolicitudReparacion>> listarTodas() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }

    /** 🔹 Listar solicitudes de un cliente específico */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudReparacion>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(solicitudService.listarPorCliente(idCliente));
    }

    /** 🔹 Cambiar el estado de una solicitud (aceptada, cancelada, en curso, etc.) */
    @PutMapping("/{id}/estado")
    @Transactional
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Estado estado) {
        try {
            SolicitudReparacion actualizada = solicitudService.actualizarEstado(id, estado);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 🔹 Eliminar una solicitud (opcional, solo admin o cliente dueño) */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarSolicitud(@PathVariable Integer id) {
        try {
            solicitudService.eliminarSolicitud(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
