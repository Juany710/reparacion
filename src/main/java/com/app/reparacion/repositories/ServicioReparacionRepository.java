package com.app.reparacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.ServicioReparacion;

public interface ServicioReparacionRepository extends JpaRepository<ServicioReparacion, Integer> {
    
        // Buscar servicios por el ID del técnico asociado a la oferta
    List<ServicioReparacion> findByOfertaTecnicoId(Integer idTecnico);
}
