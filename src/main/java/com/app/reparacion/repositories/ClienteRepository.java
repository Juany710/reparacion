package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.reparacion.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
