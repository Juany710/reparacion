package com.app.reparacion.controllers;

import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.services.ServicioReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioReparacionController {

    @Autowired
    private ServicioReparacionService servicioService;

    /** Crear un nuevo servicio al aceptar una oferta */
    @PostMapping
    @Transactional
    public ResponseEntity<?> crearServicio(@RequestBody Oferta oferta) {
        try {
            ServicioReparacion nuevo = servicioService.crearServicio(oferta);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el servicio: " + e.getMessage());
        }
    }

    /** Obtener servicio por ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return servicioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Listar todos los servicios de un técnico (historial) */
    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<?>> listarPorTecnico(@PathVariable Integer idTecnico) {
        return ResponseEntity.ok(servicioService.listarHistorialPorTecnico(idTecnico));
    }

    /** Actualizar el estado del servicio */
    @PutMapping("/{id}/estado")
    @Transactional
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestParam String nuevoEstado) {
        try {
            ServicioReparacion actualizado = servicioService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
