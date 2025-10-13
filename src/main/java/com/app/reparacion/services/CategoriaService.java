package com.app.reparacion.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.Categoria;
import com.app.reparacion.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepo.findAll();
    }

    public Categoria obtenerPorId(Integer id) {
        return categoriaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

}
