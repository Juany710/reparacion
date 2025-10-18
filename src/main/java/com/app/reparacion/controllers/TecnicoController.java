package com.app.reparacion.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.reparacion.models.Categoria;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.Tecnico;
import com.app.reparacion.services.TecnicoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/tecnicos")
@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    /* Registrar nuevo técnico */
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarTecnico(@RequestBody Tecnico tecnico) {
        try {
            Tecnico nuevo = tecnicoService.registrarTecnico(tecnico);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Obtener técnico completo con servicios, categorías y calificaciones */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> obtenerTecnicoCompleto(@PathVariable Integer id) {
        return tecnicoService.obtenerTecnicoCompleto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* Listar todos los técnicos */
    @GetMapping
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<List<Tecnico>> listarTecnicos() {
        return ResponseEntity.ok(tecnicoService.listarTecnicos());
    }

    /*  Actualizar datos de contacto */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> actualizarDatos(
            @PathVariable Integer id,
            @RequestBody Tecnico nuevosDatos) {

        try {
            Tecnico actualizado = tecnicoService.actualizarDatos(id, nuevosDatos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Listar categorías del técnico */
    @GetMapping("/{id}/categorias")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<List<Categoria>> listarCategorias(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.listarCategorias(id));
    }

    /* Actualizar categorías del técnico */
    @PutMapping("/{id}/categorias")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> actualizarCategorias(
            @PathVariable Integer id,
            @RequestBody List<Integer> idsCategorias) {

        try {
            Tecnico actualizado = tecnicoService.actualizarCategorias(id, idsCategorias);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Historial de servicios realizados */
    @GetMapping("/{id}/servicios")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<List<ServicioReparacion>> historialServicios(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.historialServicios(id));
    }
}
