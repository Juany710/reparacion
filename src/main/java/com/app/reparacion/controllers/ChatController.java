package com.app.reparacion.controllers;

import com.app.reparacion.models.Chat;
import com.app.reparacion.dto.ChatMensajeDTO;
import com.app.reparacion.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('CLIENTE','TECNICO','ADMIN','SOPORTE')")
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /** Enviar un nuevo mensaje */
    @PostMapping
    @Transactional
    public ResponseEntity<?> enviarMensaje(@RequestBody Chat chat) {
        try {
            Chat nuevo = chatService.enviarMensaje(chat);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Marcar mensaje como ENVIADO */
    @PutMapping("/{id}/enviado")
    @Transactional
    public ResponseEntity<?> marcarComoEnviado(@PathVariable Integer id) {
        return ResponseEntity.ok(chatService.marcarComoEnviado(id));
    }

    /** Marcar mensaje como RECIBIDO */
    @PutMapping("/{id}/recibido")
    @Transactional
    public ResponseEntity<?> marcarComoRecibido(@PathVariable Integer id) {
        return ResponseEntity.ok(chatService.marcarComoRecibido(id));
    }

    /** Marcar mensaje como VISTO */
    @PutMapping("/{id}/visto")
    @Transactional
    public ResponseEntity<?> marcarComoVisto(@PathVariable Integer id) {
        return ResponseEntity.ok(chatService.marcarComoVisto(id));
    }

    /** Listar mensajes por servicio */
    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<Chat>> listarPorServicio(@PathVariable Integer idServicio) {
        return ResponseEntity.ok(chatService.listarPorServicio(idServicio));
    }

    /** Listar mensajes por ticket de soporte */
    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<Chat>> listarPorTicket(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(chatService.listarPorTicket(idTicket));
    }

    /** Listar mensajes resumidos por servicio (DTO) */
    @GetMapping("/servicio/{idServicio}/resumen")
    public ResponseEntity<List<ChatMensajeDTO>> obtenerMensajesPorServicio(@PathVariable Integer idServicio) {
        return ResponseEntity.ok(chatService.obtenerMensajesPorServicio(idServicio));
    }
}
