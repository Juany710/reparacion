package com.app.reparacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.reparacion.dto.OfertaResumenDTO;
import com.app.reparacion.models.Oferta;
import jakarta.transaction.Transactional;

@Repository
public interface OfertaRepository extends JpaRepository <Oferta, Integer>{

    List<Oferta> findBySolicitudIdSolicitud(Integer idSolicitud);

    // Rechaza todas las otras ofertas de la misma solicitud
    @Modifying
    @Transactional
    @Query("UPDATE Oferta o SET o.estado = 'RECHAZADA' " +
        "WHERE o.solicitud.idSolicitud = :idSolicitud " +
        "AND o.idOferta <> :idOfertaAceptada")
    void rechazarOtras(@Param("idSolicitud") Integer idSolicitud,
                    @Param("idOfertaAceptada") Integer idOfertaAceptada);

    @Query("""
    SELECT new com.app.reparacion.dto.OfertaResumenDTO(
        o.idOferta,
        c.nombre,
        o.precio,
        o.modalidad,
        o.estado
    )
    FROM Oferta o
    JOIN o.solicitud s
    JOIN s.categoria c
    WHERE o.tecnico.idUsuario = :idTecnico
    ORDER BY o.idOferta DESC
""")
    List<OfertaResumenDTO> listarPorTecnico(@Param("idTecnico") Integer idTecnico);
}
