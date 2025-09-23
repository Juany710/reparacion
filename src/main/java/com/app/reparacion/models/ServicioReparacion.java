package com.app.reparacion.models;

import java.time.LocalDate;
import com.app.reparacion.models.enums.EstadoServicio;
import jakarta.persistence.*;

@Entity
@Table(name = "servicio_reparacion")
public class ServicioReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServicio;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoServicio estado; // EN_CURSO, FINALIZADO, CANCELADO

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL)
    private Chat chat;

    // Getters y Setters
}
