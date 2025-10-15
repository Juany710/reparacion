package com.app.reparacion.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tecnico")
public class Tecnico extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTecnico;

    private String especialidad;

    //Relación con servicios
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"tecnico", "oferta"})  // evita ciclo entre ServicioReparacion → Tecnico → ServicioReparacion
    private List<ServicioReparacion> servicios = new ArrayList<>();

    //Relación con categorías
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tecnico_categoria",
        joinColumns = @JoinColumn(name = "id_tecnico"),
        inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @JsonIgnoreProperties("tecnicos")  // evita ciclo entre Categoria → Tecnico → Categoria
    private List<Categoria> categorias = new ArrayList<>();

    //Relación con calificaciones recibidas
    @OneToMany(mappedBy = "destinatario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"destinatario", "emisor"})  // evita ciclo entre Calificacion → Usuario → Calificacion
    private List<Calificacion> calificaciones = new ArrayList<>();


    //Getters y Setters

        public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<ServicioReparacion> getServicios() {
        return servicios;
    }
    public void setServicios(List<ServicioReparacion> servicios) {
        this.servicios = servicios;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }
    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

        public Integer getIdTecnico() {
        return idTecnico;
    }
    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public List<Categoria> getCategorias() { 
        return categorias; 
    }
    public void setCategorias(List<Categoria> categorias) { 
        this.categorias = categorias; 
    }
}
