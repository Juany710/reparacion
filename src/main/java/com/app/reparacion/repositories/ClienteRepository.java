package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
