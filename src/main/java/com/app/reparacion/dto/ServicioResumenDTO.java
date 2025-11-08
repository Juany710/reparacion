package com.app.reparacion.dto;

import java.time.LocalDate;
import com.app.reparacion.models.enums.EstadoServicio;

public class ServicioResumenDTO {

    private Integer idServicio;
    private String categoria;
    private String cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoServicio estado;


    public ServicioResumenDTO (Integer idServicio, String categoria, String cliente, LocalDate fechaInicio, LocalDate fechaFin, EstadoServicio estado) {
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoServicio getEstado() {
        return estado;
    }
    public void setEstado(EstadoServicio estado) {
        this.estado = estado;
    }
}
