package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.reparacion.models.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository <Usuario, Integer>{
}
