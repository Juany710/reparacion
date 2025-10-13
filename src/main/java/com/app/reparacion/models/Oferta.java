package com.app.reparacion.models;

import java.time.LocalDate;

import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.models.enums.Modalidad;

import jakarta.persistence.*;

@Entity
@Table(name = "oferta")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOferta;

    private Double precio;
    private String garantia;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado; // PENDIENTE, ACEPTADA, RECHAZADA

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad; // Presencial, Taller

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "solicitud_idSolicitud")
    private SolicitudReparacion solicitud;

    // Getters y Setters

        public Integer getIdOferta() {
        return idOferta;
    }
    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }
    
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getGarantia() {
        return garantia;
    }
    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

        public Tecnico getTecnico() {
        return tecnico;
    }
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public SolicitudReparacion getSolicitud() {
        return solicitud;
    }
    public void setSolicitud(SolicitudReparacion solicitud) {
        this.solicitud = solicitud;
    }
}
