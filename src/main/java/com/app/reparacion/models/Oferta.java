package com.app.reparacion.models;

import java.time.LocalDate;
import com.app.reparacion.models.enums.Estado;
import com.app.reparacion.models.enums.Modalidad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "oferta")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOferta;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    private Double precio;

    @Size(max = 45, message = "La garantía no puede superar los 45 caracteres")
    private String garantia;

    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    private Estado estado; // PENDIENTE, ACEPTADA, RECHAZADA

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe indicar la modalidad de trabajo")
    private Modalidad modalidad; // Presencial, Taller

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "idtecnico")
    @NotNull(message = "Debe asociarse a un técnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "idsolicitud_reparacion", nullable = false)
    private SolicitudReparacion solicitud;

    @OneToOne(mappedBy = "oferta")
    @JsonIgnoreProperties("oferta")
    private ServicioReparacion servicio;

    // Getters y Setters

        public ServicioReparacion getServicio() {
        return servicio;
    }
    public void setServicio(ServicioReparacion servicio) {
        this.servicio = servicio;
    }

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
