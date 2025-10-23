package com.app.reparacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCalificacion;

    @NotNull(message = "El puntaje es obligatorio")
    @Min(value = 1, message = "El puntaje mínimo es 1")
    @Max(value = 5, message = "El puntaje máximo es 5")
    private Integer puntaje; // 1 a 5

    @Size(max = 200, message = "El comentario no puede superar los 200 caracteres")
    private String comentario;


    @ManyToOne
    @JoinColumn(name = "Autor", nullable = false)
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Destinatario")
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioReparacion servicio;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketSoporte ticket;

        // Getters y Setters
        public Integer getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

        public Usuario getAutor() {
        return autor;
    }
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
    
    public Usuario getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public ServicioReparacion getServicio() {
        return servicio;
    }
    public void setServicio(ServicioReparacion servicio) {
        this.servicio = servicio;
    }

    public TicketSoporte getTicket() {
        return ticket;
    }
    public void setTicket(TicketSoporte ticket) {
        this.ticket = ticket;
    }
}
