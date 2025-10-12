package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
