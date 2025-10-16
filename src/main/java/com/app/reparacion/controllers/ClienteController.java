package com.app.reparacion.controllers;

import com.app.reparacion.models.Cliente;
import com.app.reparacion.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /** Registrar un nuevo cliente */
    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevo = clienteService.registrarCliente(cliente);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar cliente: " + e.getMessage());
        }
    }

    /** Obtener un cliente completo con sus relaciones */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClienteCompleto(@PathVariable Integer id) {
        return clienteService.obtenerClienteCompleto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Actualizar datos básicos (teléfono, dirección) */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarDatos(@PathVariable Integer id, @RequestBody Cliente nuevosDatos) {
        try {
            Cliente actualizado = clienteService.actualizarDatos(id, nuevosDatos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Listar todos los clientes */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    /** Eliminar cliente (solo si no tiene solicitudes activas) */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
