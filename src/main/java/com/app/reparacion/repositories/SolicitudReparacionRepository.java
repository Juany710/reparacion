package com.app.reparacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.reparacion.dto.SolicitudResumenDTO;
import com.app.reparacion.models.SolicitudReparacion;

@Repository
public interface SolicitudReparacionRepository extends JpaRepository <SolicitudReparacion, Integer>{
    List<SolicitudReparacion> findByClienteId(Integer idCliente);
    
    @Query("""
        SELECT new com.app.reparacion.dto.SolicitudResumenDTO(
            s.idSolicitudReparacion,
            c.nombre,
            s.estado,
            s.fechaSolicitud
        )
        FROM SolicitudReparacion s
        JOIN s.categoria c
        WHERE s.cliente.idCliente = :idCliente
        ORDER BY s.fechaSolicitud DESC
    """)
    List<SolicitudResumenDTO> listarPorCliente(@Param("idCliente") Integer idCliente);
}
