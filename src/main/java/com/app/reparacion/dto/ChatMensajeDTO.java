package com.app.reparacion.dto;

import java.time.LocalDateTime;

public class ChatMensajeDTO {

    private Integer idMensaje;
    private String emisor;
    private String receptor;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaEnvio;


    public ChatMensajeDTO(Integer idMensaje, String emisor, String receptor, String mensaje, String estado, LocalDateTime fechaEnvio) {
        this.idMensaje = idMensaje;
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
    }

    //Getters & Setters
    public Integer getIdMensaje() {
        return idMensaje;
    }
    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getEmisor() {
        return emisor;
    }
    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }
    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
