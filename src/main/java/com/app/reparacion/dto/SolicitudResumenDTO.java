package com.app.reparacion.dto;

import java.time.LocalDate;
import com.app.reparacion.models.enums.Estado;

public class SolicitudResumenDTO {

    private Integer idSolicitud;
    private String categoria;
    private Estado estado;
    private LocalDate fechaSolicitud;

    public SolicitudResumenDTO(Integer idSolicitud, String categoria, Estado estado, LocalDate fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    // Getters & Setters
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

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
