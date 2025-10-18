package com.app.reparacion.dto;

import java.time.LocalDateTime;

public class ChatMensajeDTO {

    private Integer idChat;
    private String emisor;
    private String receptor;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaEnvio;


    public ChatMensajeDTO(Integer idChat, String emisor, String receptor, String mensaje, String estado, LocalDateTime fechaEnvio) {
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

    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
}
