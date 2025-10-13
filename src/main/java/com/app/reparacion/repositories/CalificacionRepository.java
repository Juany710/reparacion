package com.app.reparacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.reparacion.models.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

    List<Calificacion> findByDestinatarioId(Integer destinatarioId);

    @Query("SELECT AVG(c.puntaje) FROM Calificacion c WHERE c.destinatario.id = :id")
    Double promedioPorDestinatario(@Param("id") Integer destinatarioId);
}
