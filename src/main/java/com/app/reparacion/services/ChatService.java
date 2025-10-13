package com.app.reparacion.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.app.reparacion.dto.ChatMensajeDTO;
import com.app.reparacion.models.Chat;
import com.app.reparacion.models.enums.EstadoMensaje;
import com.app.reparacion.repositories.ChatRepository;
import jakarta.transaction.Transactional;


@Service
public class ChatService {

    private final ChatRepository chatRepo;

    public ChatService(ChatRepository chatRepo) {
        this.chatRepo = chatRepo;
    }

     /** Enviar un mensaje nuevo */
    @Transactional
    public Chat enviarMensaje(Chat chat) {
        chat.setFechaEnvio(LocalDateTime.now());
        return chatRepo.save(chat);
    }

     /** Marcar un mensaje como enviado */
    public Chat marcarComoEnviado(Integer id) {
        Chat chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        chat.setEstado(EstadoMensaje.ENVIADO);
        return chatRepo.save(chat);
    }

     /** Marcar un mensaje como recibido */
    public Chat marcarComoRecibido(Integer id) {
        Chat chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        chat.setEstado(EstadoMensaje.RECIBIDO);
        return chatRepo.save(chat);
    }

    /** Marcar un mensaje como visto */
    public Chat marcarComoVisto(Integer id) {
        Chat chat = chatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        chat.setEstado(EstadoMensaje.VISTO);
        return chatRepo.save(chat);
    }

    public List<Chat> listarPorServicio(Integer idServicio) {
        return chatRepo.findByServicioId(idServicio);
    }

    public List<Chat> listarPorTicket(Integer idTicket) {
        return chatRepo.findByTicketId(idTicket);
    }

    public List<ChatMensajeDTO> obtenerMensajesPorServicio(Integer idServicio) {
        return chatRepo.listarPorServicio(idServicio);
    }
}
