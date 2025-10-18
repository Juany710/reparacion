package com.app.reparacion.controllers;

import com.app.reparacion.models.Oferta;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.services.OfertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    /* Crear una nueva oferta (técnico postula a una solicitud) */
    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<?> crearOferta(@RequestBody Oferta oferta) {
        try {
            Oferta nueva = ofertaService.crearOferta(oferta);
            return ResponseEntity.ok(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Listar todas las ofertas de una solicitud */
    @GetMapping("/solicitud/{idSolicitud}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<List<Oferta>> listarPorSolicitud(@PathVariable Integer idSolicitud) {
        return ResponseEntity.ok(ofertaService.listarPorSolicitud(idSolicitud));
    }

    /* Listar ofertas enviadas por un técnico */
    @GetMapping("/tecnico/{idTecnico}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> listarPorTecnico(@PathVariable Integer idTecnico) {
        return ResponseEntity.ok(ofertaService.obtenerOfertasPorTecnico(idTecnico));
    }

    /* Aceptar una oferta (crea automáticamente el servicio de reparación) */
    @PutMapping("/{id}/aceptar")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @Transactional
    public ResponseEntity<?> aceptarOferta(@PathVariable Integer id) {
        try {
            ServicioReparacion servicio = ofertaService.aceptarOferta(id);
            return ResponseEntity.ok(servicio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Rechazar una oferta */
    @PutMapping("/{id}/rechazar")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @Transactional
    public ResponseEntity<?> rechazarOferta(@PathVariable Integer id) {
        try {
            Oferta rechazada = ofertaService.rechazarOferta(id);
            return ResponseEntity.ok(rechazada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Obtener una oferta por ID */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return ofertaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
