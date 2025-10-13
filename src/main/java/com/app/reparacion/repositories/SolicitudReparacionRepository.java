package com.app.reparacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.SolicitudReparacion;

public interface SolicitudReparacionRepository extends JpaRepository <SolicitudReparacion, Integer>{
    List<SolicitudReparacion> findByClienteId(Integer idCliente);
    
}
