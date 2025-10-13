package com.app.reparacion.dto;

import java.time.LocalDateTime;

public class ServicioResumenDTO {

    private Integer idServicio;
    private String categoria;
    private String cliente;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;


    public ServicioResumenDTO (Integer idServicio, String categoria, String cliente, LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado) {
        this.idServicio = idServicio;
        this.categoria = categoria;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    //Getters & Setters 

    public Integer getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
