package com.app.reparacion.services;

import com.app.reparacion.models.Ubicacion;
import com.app.reparacion.models.Tecnico;
import com.app.reparacion.repositories.UbicacionRepository;
import com.app.reparacion.repositories.TecnicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UbicacionService {
    
    private static final Logger logger = LoggerFactory.getLogger(UbicacionService.class);
    
    @Autowired
    private UbicacionRepository ubicacionRepository;
    
    @Autowired
    private TecnicoRepository tecnicoRepository;
    
    public Ubicacion guardarUbicacion(Integer idTecnico, Double latitud, Double longitud) {
        Ubicacion ubicacion = new Ubicacion();
        Tecnico tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));
        ubicacion.setTecnico(tecnico);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);
        ubicacion.setTimestamp(LocalDateTime.now());
        
        Ubicacion guardada = ubicacionRepository.save(ubicacion);
        logger.info("Ubicación guardada para técnico: {} en lat: {}, lng: {}", 
                idTecnico, latitud, longitud);
        return guardada;
    }
    
    public Optional<Ubicacion> obtenerUltimaUbicacion(Integer idTecnico) {
        return ubicacionRepository.findFirstByTecnico_IdUsuarioOrderByTimestampDesc(idTecnico);
    }
    
    public List<Ubicacion> obtenerHistorialUbicaciones(Integer idTecnico) {
        return ubicacionRepository.findUbicacionesPorTecnico(idTecnico);
    }
    
    public List<Ubicacion> obtenerUbicacionesPorRango(Integer idTecnico, 
                                                      LocalDateTime inicio, 
                                                      LocalDateTime fin) {
        return ubicacionRepository.findUbicacionesPorTecnicoYRango(idTecnico, inicio, fin);
    }
    
    public Optional<Ubicacion> obtenerUbicacionPorId(Integer idUbicacion) {
        return ubicacionRepository.findById(idUbicacion);
    }
    
    public double calcularDistancia(Double lat1, Double lng1, Double lat2, Double lng2) {
        final int RADIO_TIERRA_KM = 6371;
        
        Double dLat = Math.toRadians(lat2 - lat1);
        Double dLng = Math.toRadians(lng2 - lng1);
        
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2);
        
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }
    
    public void limpiarUbicacionesAntiguas(Integer diasRetension) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(diasRetension);
        List<Ubicacion> ubicacionesAntiguas = ubicacionRepository
                .findUbicacionesPorRango(LocalDateTime.of(1970, 1, 1, 0, 0), fechaLimite);
        
        if (!ubicacionesAntiguas.isEmpty()) {
            ubicacionRepository.deleteAll(ubicacionesAntiguas);
            logger.info("Eliminadas {} ubicaciones antiguas", ubicacionesAntiguas.size());
        }
    }
}
