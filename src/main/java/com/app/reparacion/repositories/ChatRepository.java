package com.app.reparacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.reparacion.dto.ChatMensajeDTO;
import com.app.reparacion.models.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByServicio_IdServicio(Integer idServicio);

    List<Chat> findByTicket_IdTicket(Integer idTicket);

    @Query("""
    SELECT new com.app.reparacion.dto.ChatMensajeDTO(
        m.idChat,
        m.emisor,
        m.receptor,
        m,
        m.estado,
        m.fechaEnvio
    )
    FROM Chat m
    WHERE m.servicio.idServicio = :idServicio
    ORDER BY m.fechaEnvio ASC
    """)
    List<ChatMensajeDTO> listarPorServicio(@Param("idServicio") Integer idServicio);
}
