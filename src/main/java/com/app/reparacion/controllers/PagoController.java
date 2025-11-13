package com.app.reparacion.controllers;

import com.app.reparacion.dto.PagoRequestDTO;
import com.app.reparacion.dto.PagoResponseDTO;
import com.app.reparacion.models.Pago;
import com.app.reparacion.models.enums.EstadoPago;
import com.app.reparacion.services.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(
            @Valid @RequestBody PagoRequestDTO request,
            @RequestParam Integer idCliente) {
        try {
            PagoResponseDTO respuesta = pagoService.procesarPago(request, idCliente);
            if (respuesta.getEstado() == EstadoPago.COMPLETADO) {
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else if (respuesta.getEstado() == EstadoPago.PENDIENTE) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando pago: " + e.getMessage());
        }
    }
    
    @GetMapping("/historial")
    public ResponseEntity<?> obtenerHistorial(@RequestParam Integer idCliente) {
        try {
            List<Pago> pagos = pagoService.obtenerHistorialPagosCliente(idCliente);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo historial: " + e.getMessage());
        }
    }
    
    @GetMapping("/{idPago}")
    public ResponseEntity<?> obtenerPago(@PathVariable Integer idPago) {
        try {
            Optional<Pago> pago = pagoService.obtenerPagoPorId(idPago);
            if (pago.isPresent()) {
                return ResponseEntity.ok(pagoService.convertirAPagoResponseDTO(pago.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo pago: " + e.getMessage());
        }
    }
    
    @GetMapping("/referencia/{referencia}")
    public ResponseEntity<?> obtenerPagoPorReferencia(@PathVariable String referencia) {
        try {
            Optional<Pago> pago = pagoService.obtenerPagoPorReferencia(referencia);
            if (pago.isPresent()) {
                return ResponseEntity.ok(pagoService.convertirAPagoResponseDTO(pago.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo pago: " + e.getMessage());
        }
    }
    
    @PostMapping("/{idPago}/reembolsar")
    public ResponseEntity<?> reembolsar(@PathVariable Integer idPago) {
        try {
            pagoService.reembolsar(idPago);
            return ResponseEntity.ok("Reembolso procesado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando reembolso: " + e.getMessage());
        }
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPagosPorEstado(@PathVariable EstadoPago estado) {
        try {
            List<Pago> pagos = pagoService.obtenerPagosPorEstado(estado);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obteniendo pagos: " + e.getMessage());
        }
    }
}
