package com.app.reparacion.controllers;

import com.app.reparacion.models.Calificacion;
import com.app.reparacion.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('CLIENTE','TECNICO','ADMIN','SOPORTE')")
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "*")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    /** Registrar nueva calificación */
    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarCalificacion(@RequestBody Calificacion calificacion) {
        try {
            Calificacion nueva = calificacionService.registrarCalificacion(calificacion);
            return ResponseEntity.ok(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Listar calificaciones de un usuario (destinatario) */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Calificacion>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(calificacionService.listarPorDestinatario(idUsuario));
    }

    /** Obtener promedio de calificaciones de un usuario */
    @GetMapping("/usuario/{idUsuario}/promedio")
    public ResponseEntity<Double> obtenerPromedio(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(calificacionService.calcularPromedioPorDestinatario(idUsuario));
    }
}
