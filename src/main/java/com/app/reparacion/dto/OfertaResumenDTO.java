package com.app.reparacion.dto;

import java.math.BigDecimal;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.models.enums.Modalidad;

public class OfertaResumenDTO {

    private Integer idOferta;
    private String categoria;
    private BigDecimal precio;
    private Modalidad modalidad;
    private Estado estado;


    public OfertaResumenDTO(Integer idOferta, String categoria, BigDecimal precio, Modalidad modalidad, Estado estado) {
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

    public Modalidad getModalidad() {
        return modalidad;
    }
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
