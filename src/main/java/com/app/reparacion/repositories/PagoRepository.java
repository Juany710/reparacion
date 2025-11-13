package com.app.reparacion.repositories;

import com.app.reparacion.models.Pago;
import com.app.reparacion.models.enums.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    // Campo en la entidad: referencia_stripe (con guion bajo). Usamos JPQL explícito para evitar problemas con los métodos derivados.
    @Query("SELECT p FROM Pago p WHERE p.referencia_stripe = :ref")
    Optional<Pago> findByReferencia_stripe(@Param("ref") String ref);
    
    // Consultas por el cliente (Cliente hereda de Usuario: idUsuario)
    List<Pago> findByCliente_IdUsuario(Integer idUsuario);
    
    List<Pago> findByEstado(EstadoPago estado);
    
    List<Pago> findByCliente_IdUsuarioAndEstado(Integer idUsuario, EstadoPago estado);
    
    @Query("SELECT p FROM Pago p WHERE p.cliente.idUsuario = :idUsuario ORDER BY p.fecha DESC")
    List<Pago> findPagosClientePorFecha(@Param("idUsuario") Integer idUsuario);
    
    @Query("SELECT p FROM Pago p WHERE p.fecha BETWEEN :inicio AND :fin")
    List<Pago> findPagosPorRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
