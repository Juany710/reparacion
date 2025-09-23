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

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private SolicitudReparacion solicitud;

    // Getters y Setters
}
