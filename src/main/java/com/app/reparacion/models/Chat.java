package com.app.reparacion.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChat;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioReparacion servicio;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketSoporte ticket;

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
