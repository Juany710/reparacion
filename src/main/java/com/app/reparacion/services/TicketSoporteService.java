package com.app.reparacion.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.TicketSoporte;
import com.app.reparacion.models.enums.EstadoTicket;
import com.app.reparacion.repositories.TicketSoporteRepository;

@Service
public class TicketSoporteService {

    private final TicketSoporteRepository ticketRepo;

    public TicketSoporteService(TicketSoporteRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public TicketSoporte crearTicket(TicketSoporte ticket) {
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setEstado(EstadoTicket.ABIERTO);
        return ticketRepo.save(ticket);
    }

    public TicketSoporte cerrarTicket(Integer id) {
        TicketSoporte ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        ticket.setEstado(EstadoTicket.CERRADO);
        return ticketRepo.save(ticket);
    }
}
