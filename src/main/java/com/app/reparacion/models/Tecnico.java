package com.app.reparacion.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnico")
public class Tecnico extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTecnico")
    private Integer idTecnico;
    
    @ManyToMany
    @JoinTable(
        name = "tecnico_has_categoria",
        joinColumns = @JoinColumn(name = "usuario_idUsuario"),
        inverseJoinColumns = @JoinColumn(name = "categoria_idCategoria")
    )
    @JsonIgnoreProperties("tecnicos")
    private List<Categoria> categorias;

    // 🔹 Getters y Setters
    public List<Categoria> getCategorias() { 
        return categorias; 
    }
    public void setCategorias(List<Categoria> categorias) { 
        this.categorias = categorias; 
    }
}
