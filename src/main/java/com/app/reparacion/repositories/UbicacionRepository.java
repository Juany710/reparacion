package com.app.reparacion.repositories;

import com.app.reparacion.models.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    // La entidad tiene relacion ManyToOne Tecnico tecnico; por tanto las propiedades derivadas usan 'tecnico'.
    List<Ubicacion> findByTecnico_IdUsuario(Integer idUsuario);

    // Ultima ubicacion del técnico (JPA genera LIMIT 1 equivalente)
    Optional<Ubicacion> findFirstByTecnico_IdUsuarioOrderByTimestampDesc(Integer idUsuario);

    @Query("SELECT u FROM Ubicacion u WHERE u.tecnico.idUsuario = :idUsuario ORDER BY u.timestamp DESC")
    List<Ubicacion> findUbicacionesPorTecnico(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT u FROM Ubicacion u WHERE u.tecnico.idUsuario = :idUsuario AND u.timestamp BETWEEN :inicio AND :fin ORDER BY u.timestamp DESC")
    List<Ubicacion> findUbicacionesPorTecnicoYRango(
            @Param("idUsuario") Integer idUsuario,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

    @Query("SELECT u FROM Ubicacion u WHERE u.timestamp BETWEEN :inicio AND :fin ORDER BY u.timestamp DESC")
    List<Ubicacion> findUbicacionesPorRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
