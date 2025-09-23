package com.app.reparacion.models;

import jakarta.persistence.*;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCalificacion;

    private Integer puntaje; // 1 a 5
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioReparacion servicio;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketSoporte ticket;

    // Getters y Setters
}
