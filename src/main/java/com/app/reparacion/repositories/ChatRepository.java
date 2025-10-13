package com.app.reparacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.reparacion.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByServicioId(Integer idServicio);

    List<Chat> findByTicketId(Integer idTicket);

}
