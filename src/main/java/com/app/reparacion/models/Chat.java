package com.app.reparacion.models;

import java.time.LocalDateTime;
import com.app.reparacion.models.enums.EstadoMensaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChat;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @PastOrPresent(message = "La fecha de envío no puede ser futura")
    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mensaje")
    @NotNull(message = "El estado del mensaje es obligatorio")
    private EstadoMensaje estado = EstadoMensaje.PENDIENTE; // valor por defecto

    @ManyToOne
    @JoinColumn(name = "Emisor", nullable = false)
    @NotNull(message = "Debe indicar el emisor del mensaje")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "Receptor", nullable = false)
    @NotNull(message = "Debe indicar el receptor del mensaje")
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

        public EstadoMensaje getEstado() {
        return estado;
    }
    public void setEstado(EstadoMensaje estado) {
        this.estado = estado;
    }

    public Usuario getEmisor() {
        return emisor;
    }
    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }
    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
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
