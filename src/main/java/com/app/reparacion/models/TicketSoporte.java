package com.app.reparacion.models;

import java.time.LocalDateTime;
import java.util.List;
import com.app.reparacion.models.enums.EstadoTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_soporte")
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoTicket estado; // ABIERTO, EN_PROCESO, CERRADO

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties("ticketSoporte")
    private List<Chat> chats;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties("ticketSoporte")
    private List<Calificacion> calificaciones;

    // Getters y Setters
    
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTicket getEstado() {
        return estado;
    }
    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
