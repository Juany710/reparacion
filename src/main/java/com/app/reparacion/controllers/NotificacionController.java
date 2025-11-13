package com.app.reparacion.controllers;

import com.app.reparacion.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    
    @Autowired
    private FirebaseService firebaseService;
    
    @PostMapping("/registrar-dispositivo")
    public ResponseEntity<?> registrarDispositivo(
            @RequestParam Integer idUsuario,
            @RequestParam String token,
            @RequestParam String plataforma) {
        try {
            firebaseService.registrarDispositivo(idUsuario, token, plataforma);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Dispositivo registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registrando dispositivo: " + e.getMessage());
        }
    }
    
    @PostMapping("/desregistrar-dispositivo")
    public ResponseEntity<?> desregistrarDispositivo(@RequestParam String token) {
        try {
            firebaseService.desregistrarDispositivo(token);
            return ResponseEntity.ok("Dispositivo desregistrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error desregistrando dispositivo: " + e.getMessage());
        }
    }
    
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarNotificacion(
            @RequestParam String token,
            @RequestParam String titulo,
            @RequestParam String cuerpo,
            @RequestParam(required = false) String icono,
            @RequestParam(required = false) String sonido) {
        try {
            firebaseService.enviarNotificacion(token, titulo, cuerpo, icono, sonido);
            return ResponseEntity.ok("Notificación enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando notificación: " + e.getMessage());
        }
    }
    
    @PostMapping("/enviar-multicast")
    public ResponseEntity<?> enviarNotificacionMulticast(
            @RequestBody List<String> tokens,
            @RequestParam String titulo,
            @RequestParam String cuerpo,
            @RequestParam(required = false) String icono,
            @RequestParam(required = false) String sonido) {
        try {
            firebaseService.enviarNotificacionMulticast(tokens, titulo, cuerpo, icono, sonido);
            return ResponseEntity.ok("Notificaciones enviadas exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando notificaciones: " + e.getMessage());
        }
    }
    
    @PostMapping("/tecnico/{idTecnico}")
    public ResponseEntity<?> enviarNotificacionATecnico(
            @PathVariable Integer idTecnico,
            @RequestParam String titulo,
            @RequestParam String cuerpo) {
        try {
            firebaseService.enviarNotificacionATecnico(idTecnico, titulo, cuerpo);
            return ResponseEntity.ok("Notificaciones enviadas al técnico");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando notificaciones: " + e.getMessage());
        }
    }
    
    @PostMapping("/tema/{tema}")
    public ResponseEntity<?> enviarNotificacionTema(
            @PathVariable String tema,
            @RequestParam String titulo,
            @RequestParam String cuerpo) {
        try {
            firebaseService.enviarNotificacionTema(tema, titulo, cuerpo);
            return ResponseEntity.ok("Notificación de tema enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando notificación: " + e.getMessage());
        }
    }
    
    @PostMapping("/suscribir-tema")
    public ResponseEntity<?> suscribirATema(
            @RequestParam String token,
            @RequestParam String tema) {
        try {
            firebaseService.suscribirATema(token, tema);
            return ResponseEntity.ok("Suscripción a tema exitosa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error suscribiendo a tema: " + e.getMessage());
        }
    }
    
    @PostMapping("/desuscribir-tema")
    public ResponseEntity<?> desuscribirDeTema(
            @RequestParam String token,
            @RequestParam String tema) {
        try {
            firebaseService.desuscribirDeTema(token, tema);
            return ResponseEntity.ok("Desuscripción de tema exitosa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error desuscribiendo de tema: " + e.getMessage());
        }
    }
}
