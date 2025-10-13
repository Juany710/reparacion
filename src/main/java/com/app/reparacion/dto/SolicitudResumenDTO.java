package com.app.reparacion.dto;

import java.time.LocalDateTime;

public class SolicitudResumenDTO {

    private Integer idSolicitud;
    private String categoria;
    private String estado;
    private LocalDateTime fechaSolicitud;


    public SolicitudResumenDTO(Integer idSolicitud, String categoria, String estado, LocalDateTime fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    //Getters & Setters
        public Integer getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
