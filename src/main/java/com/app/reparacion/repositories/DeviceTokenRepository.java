package com.app.reparacion.repositories;

import com.app.reparacion.models.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer> {
    
    Optional<DeviceToken> findByToken(String token);
    
    // Buscar por el id del usuario asociado (propiedad: Usuario.idUsuario)
    List<DeviceToken> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<DeviceToken> findByPlataforma(String plataforma);
    
    @Query("SELECT d FROM DeviceToken d WHERE d.usuario.idUsuario = :idUsuario AND d.plataforma = :plataforma")
    List<DeviceToken> findTokensByUsuarioYPlataforma(@Param("idUsuario") Integer idUsuario, @Param("plataforma") String plataforma);
    
    void deleteByToken(String token);
}
