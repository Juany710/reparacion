package com.app.reparacion.dto;

import java.time.LocalDateTime;

import com.app.reparacion.models.Chat;
import com.app.reparacion.models.Usuario;
import com.app.reparacion.models.enums.EstadoMensaje;

public class ChatMensajeDTO {
    private Integer idChat;
    private Usuario emisor;
    private Usuario receptor;
    private Chat mensaje;
    private EstadoMensaje estado;
    private LocalDateTime fechaEnvio;

    public ChatMensajeDTO(Integer idChat, Usuario emisor, Usuario receptor, Chat mensaje, EstadoMensaje estado, LocalDateTime fechaEnvio) {
        this.idChat = idChat;
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
    }

    // Getters y setters
    public Integer getIdChat() {
        return idChat;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public Chat getMensaje() {
        return mensaje;
    }

    public EstadoMensaje getEstado() {
        return estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
}
