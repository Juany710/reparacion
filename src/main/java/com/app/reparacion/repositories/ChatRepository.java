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

    List<Chat> findByServicioId(Integer idServicio);

    List<Chat> findByTicketId(Integer idTicket);

    @Query("""
        SELECT new com.app.reparacion.dto.ChatMensajeDTO(
            m.idchat,
            e.nombre,
            r.nombre,
            m.mensaje,
            m.estado,
            m.fechaEnvio
        )
        FROM Chat m
        JOIN m.emisor e
        JOIN m.receptor r
        WHERE m.servicio.idservicio = :idServicio
        ORDER BY m.fechaEnvio ASC
    """)
    List<ChatMensajeDTO> listarPorServicio(@Param("idServicio") Integer idServicio);
}
