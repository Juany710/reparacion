package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.ServicioReparacion;

public interface ServicioReparacionRepository extends JpaRepository<ServicioReparacion, Integer> {

}
