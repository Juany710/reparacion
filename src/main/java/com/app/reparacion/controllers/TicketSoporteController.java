package com.app.reparacion.controllers;

import com.app.reparacion.models.TicketSoporte;
import com.app.reparacion.services.TicketSoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketSoporteController {

    @Autowired
    private TicketSoporteService ticketService;

    /** Crear nuevo ticket */
    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('CLIENTE','TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<?> crearTicket(@RequestBody TicketSoporte ticket) {
        try {
            TicketSoporte nuevo = ticketService.crearTicket(ticket);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Cerrar un ticket existente */
    @PutMapping("/{id}/cerrar")
    @Transactional
    @PreAuthorize("hasRole('SOPORTE')")
    public ResponseEntity<?> cerrarTicket(@PathVariable Integer id) {
        try {
            TicketSoporte cerrado = ticketService.cerrarTicket(id);
            return ResponseEntity.ok(cerrado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Listar todos los tickets */
    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','TECNICO','ADMIN','SOPORTE')")
    public ResponseEntity<List<TicketSoporte>> listarTickets() {
        return ResponseEntity.ok(ticketService.listarTodos());
    }
}
