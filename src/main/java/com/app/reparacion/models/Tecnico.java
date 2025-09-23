package com.app.reparacion.models;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "tecnico")
public class Tecnico extends Usuario{

    @ManyToMany
    @JoinTable(
        name = "tecnico_has_categoria",
        joinColumns = @JoinColumn(name = "usuario_idUsuario"),
        inverseJoinColumns = @JoinColumn(name = "categoria_idCategoria")
    )
    private List<Categoria> categorias;

    // 🔹 Getters y Setters
    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
}
