package com.app.reparacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
