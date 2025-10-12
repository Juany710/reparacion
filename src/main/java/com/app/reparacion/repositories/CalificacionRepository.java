package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

}
