package com.app.reparacion.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tecnico")
public class Tecnico extends Usuario{

    private String especialidad;

    //Relación con categorías
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "tecnico_has_categoria",  // ✅ más intuitivo
    joinColumns = @JoinColumn(name = "idtecnico"),
    inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )

    @JsonIgnoreProperties("tecnicos")  // evita ciclo entre Categoria → Tecnico → Categoria
    private List<Categoria> categorias = new ArrayList<>();

    //Relación con servicios
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"tecnico", "oferta"})  // evita ciclo entre ServicioReparacion → Tecnico → ServicioReparacion
    private List<ServicioReparacion> servicios = new ArrayList<>();

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

    public List<Categoria> getCategorias() { 
        return categorias; 
    }
    public void setCategorias(List<Categoria> categorias) { 
        this.categorias = categorias; 
    }
}
