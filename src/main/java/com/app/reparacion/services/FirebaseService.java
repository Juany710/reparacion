package com.app.reparacion.services;

import com.app.reparacion.models.DeviceToken;
import com.app.reparacion.repositories.DeviceTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirebaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(FirebaseService.class);
    
    @Autowired
    private DeviceTokenRepository deviceTokenRepository;
    
    public void registrarDispositivo(Integer idUsuario, String token, String plataforma) {
        DeviceToken dispositivoExistente = deviceTokenRepository.findByToken(token)
                .orElse(null);
        
        if (dispositivoExistente != null) {
            logger.info("Token ya registrado: {}", token);
            return;
        }
        
        DeviceToken nuevoDispositivo = new DeviceToken();
        nuevoDispositivo.setToken(token);
        nuevoDispositivo.setPlataforma(plataforma);
        nuevoDispositivo.setFecha_registro(LocalDateTime.now());
        
        deviceTokenRepository.save(nuevoDispositivo);
        logger.info("Dispositivo registrado para usuario: {} en plataforma: {}", idUsuario, plataforma);
    }
    
    public void desregistrarDispositivo(String token) {
        deviceTokenRepository.deleteByToken(token);
        logger.info("Dispositivo desregistrado: {}", token);
    }
    
    public void enviarNotificacion(String token, String titulo, String cuerpo, String icono, String sonido) throws Exception {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody(cuerpo)
                            .build())
                    .setToken(token)
                    .putData("icon", icono != null ? icono : "ic_notification")
                    .putData("sound", sonido != null ? sonido : "default")
                    .build();
            
            String messageId = FirebaseMessaging.getInstance().send(message);
            logger.info("Notificación enviada exitosamente. MessageId: {}", messageId);
        } catch (Exception e) {
            logger.error("Error enviando notificación a token {}: {}", token, e.getMessage());
            throw e;
        }
    }
    
    public void enviarNotificacionMulticast(List<String> tokens, String titulo, String cuerpo, String icono, String sonido) throws Exception {
        if (tokens == null || tokens.isEmpty()) {
            logger.warn("Lista de tokens vacía");
            return;
        }
        
        try {
            // sendAll() deprecated in some versions; use individual send() calls for better control
            int exitosas = 0;
            int fallidas = 0;
            
            for (String token : tokens) {
                try {
                    Message message = Message.builder()
                            .setNotification(Notification.builder()
                                    .setTitle(titulo)
                                    .setBody(cuerpo)
                                    .build())
                            .setToken(token)
                            .putData("icon", icono != null ? icono : "ic_notification")
                            .putData("sound", sonido != null ? sonido : "default")
                            .build();
                    
                    FirebaseMessaging.getInstance().send(message);
                    exitosas++;
                } catch (Exception e) {
                    logger.warn("Error enviando a token {}: {}", token, e.getMessage());
                    fallidas++;
                }
            }
            
            logger.info("Notificaciones multicast enviadas. Exitosas: {}, Fallidas: {}", exitosas, fallidas);
        } catch (Exception e) {
            logger.error("Error enviando notificaciones multicast: {}", e.getMessage());
            throw e;
        }
    }
    
    public void enviarNotificacionATecnico(Integer idTecnico, String titulo, String cuerpo) throws Exception {
        // Ajustado al nuevo método del repositorio: findByUsuario_IdUsuario
        List<DeviceToken> dispositivos = deviceTokenRepository.findByUsuario_IdUsuario(idTecnico);
        
        if (dispositivos.isEmpty()) {
            logger.warn("No hay dispositivos registrados para técnico: {}", idTecnico);
            return;
        }
        
        List<String> tokens = dispositivos.stream()
                .map(DeviceToken::getToken)
                .collect(Collectors.toList());
        
        enviarNotificacionMulticast(tokens, titulo, cuerpo, null, null);
    }
    
    public void enviarNotificacionTema(String tema, String titulo, String cuerpo) throws Exception {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody(cuerpo)
                            .build())
                    .setTopic(tema)
                    .putData("icon", "ic_notification")
                    .putData("sound", "default")
                    .build();
            
            String messageId = FirebaseMessaging.getInstance().send(message);
            logger.info("Notificación de tema enviada. MessageId: {}", messageId);
        } catch (Exception e) {
            logger.error("Error enviando notificación a tema {}: {}", tema, e.getMessage());
            throw e;
        }
    }
    
    public void suscribirATema(String token, String tema) throws Exception {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(java.util.Arrays.asList(token), tema);
            logger.info("Token suscrito a tema: {}", tema);
        } catch (Exception e) {
            logger.error("Error suscribiendo a tema: {}", e.getMessage());
            throw e;
        }
    }
    
    public void desuscribirDeTema(String token, String tema) throws Exception {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(java.util.Arrays.asList(token), tema);
            logger.info("Token desuscrito de tema: {}", tema);
        } catch (Exception e) {
            logger.error("Error desuscribiendo de tema: {}", e.getMessage());
            throw e;
        }
    }
}
