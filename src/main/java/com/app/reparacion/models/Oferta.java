package com.app.reparacion.models;

import java.time.LocalDate;
import com.app.reparacion.models.enums.EstadoOferta;
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
    private String modalidad;


    @Enumerated(EnumType.STRING)
    private EstadoOferta estado; // PENDIENTE, ACEPTADA, RECHAZADA

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private SolicitudReparacion solicitud;

    // Getters y Setters
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

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getModalidad() {
        return modalidad;
    }
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
