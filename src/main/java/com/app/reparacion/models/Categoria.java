package com.app.reparacion.models;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    private String tipo;
    private String nombre;
    private String marca;
    private String numeroSerie;
    private String patente;
    private Integer anio;

    @OneToMany(mappedBy = "categoria")
    private List<SolicitudReparacion> solicitudes;

    // Getters y Setters
}
