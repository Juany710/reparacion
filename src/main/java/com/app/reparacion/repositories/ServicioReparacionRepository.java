package com.app.reparacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.reparacion.dto.ServicioResumenDTO;
import com.app.reparacion.models.ServicioReparacion;


@Repository
public interface ServicioReparacionRepository extends JpaRepository<ServicioReparacion, Integer> {

        // Buscar servicios por el ID del técnico asociado a la oferta
        // El id del técnico se hereda de Usuario (idUsuario), por eso usamos 'IdUsuario' en el nombre
        List<ServicioReparacion> findByOfertaTecnicoIdUsuario(Integer idTecnico);

    @Query("""
SELECT new com.app.reparacion.dto.ServicioResumenDTO(
        s.idServicio,
        c.nombre,
        CONCAT(cl.nombre, ' ', cl.apellido),
        s.fechaInicio,
        s.fechaFin,
        s.estado
)
FROM ServicioReparacion s
JOIN s.oferta o
JOIN o.tecnico t
JOIN o.solicitud csol
JOIN csol.categoria c
JOIN csol.cliente cl
WHERE t.idUsuario = :idTecnico
""")
List<ServicioResumenDTO> listarHistorialServicios(@Param("idTecnico") Integer idTecnico);

}
