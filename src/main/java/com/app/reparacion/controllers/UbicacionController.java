package com.app.reparacion.controllers;

import com.app.reparacion.models.Ubicacion;
import com.app.reparacion.services.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {
    
    @Autowired
    private UbicacionService ubicacionService;
    
    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarUbicacion(
            @RequestParam Integer idTecnico,
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try {
            Ubicacion ubicacion = ubicacionService.guardarUbicacion(idTecnico, latitud, longitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(ubicacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando ubicación: " + e.getMessage());
        }
    }
    
    @GetMapping("/tecnico/{idTecnico}/ultima")
    public ResponseEntity<?> obtenerUltimaUbicacion(@PathVariable Integer idTecnico) {
        try {
            Optional<Ubicacion> ubicacion = ubicacionService.obtenerUltimaUbicacion(idTecnico);
            if (ubicacion.isPresent()) {
                return ResponseEntity.ok(ubicacion.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay ubicación registrada para este técnico");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo ubicación: " + e.getMessage());
        }
    }
    
    @GetMapping("/tecnico/{idTecnico}/historial")
    public ResponseEntity<?> obtenerHistorialUbicaciones(@PathVariable Integer idTecnico) {
        try {
            List<Ubicacion> ubicaciones = ubicacionService.obtenerHistorialUbicaciones(idTecnico);
            return ResponseEntity.ok(ubicaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo historial: " + e.getMessage());
        }
    }
    
    @GetMapping("/tecnico/{idTecnico}/rango")
    public ResponseEntity<?> obtenerUbicacionesPorRango(
            @PathVariable Integer idTecnico,
            @RequestParam String inicio,
            @RequestParam String fin) {
        try {
            LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
            LocalDateTime fechaFin = LocalDateTime.parse(fin);
            
            List<Ubicacion> ubicaciones = ubicacionService.obtenerUbicacionesPorRango(
                    idTecnico, fechaInicio, fechaFin);
            return ResponseEntity.ok(ubicaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo ubicaciones: " + e.getMessage());
        }
    }
    
    @GetMapping("/{idUbicacion}")
    public ResponseEntity<?> obtenerUbicacion(@PathVariable Integer idUbicacion) {
        try {
            Optional<Ubicacion> ubicacion = ubicacionService.obtenerUbicacionPorId(idUbicacion);
            if (ubicacion.isPresent()) {
                return ResponseEntity.ok(ubicacion.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ubicación no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo ubicación: " + e.getMessage());
        }
    }
    
    @GetMapping("/distancia")
    public ResponseEntity<?> calcularDistancia(
            @RequestParam Double lat1,
            @RequestParam Double lng1,
            @RequestParam Double lat2,
            @RequestParam Double lng2) {
        try {
            double distancia = ubicacionService.calcularDistancia(lat1, lng1, lat2, lng2);
            return ResponseEntity.ok("Distancia: " + distancia + " km");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calculando distancia: " + e.getMessage());
        }
    }
}
