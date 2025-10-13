package com.app.reparacion.dto;

import java.math.BigDecimal;

public class OfertaResumenDTO {

    private Integer idOferta;
    private String categoria;
    private BigDecimal precio;
    private String modalidad;
    private String estado;


    public OfertaResumenDTO(Integer idOferta, String categoria, BigDecimal precio, String modalidad, String estado) {
        this.idOferta = idOferta;
        this.categoria = categoria;
        this.precio = precio;
        this.modalidad = modalidad;
        this.estado = estado;
    }

    //Getters & Setters
    public Integer getIdOferta() {
        return idOferta;
    }
    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getModalidad() {
        return modalidad;
    }
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
