package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.app.reparacion.models.Cliente;
import com.app.reparacion.repositories.ClienteRepository;
import jakarta.transaction.Transactional;

public class ClienteService {

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    /** Registrar nuevo cliente con validaciones */
    @Transactional
    public Cliente registrarCliente(Cliente cliente) {
        if (clienteRepo.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (clienteRepo.existsByUsername(cliente.getUsername())) {
    throw new IllegalArgumentException("El DNI o username ya está registrado");
    }
        cliente.setFechaRegistro(LocalDate.now());
        return clienteRepo.save(cliente);
    }

    /** Obtener cliente con solicitudes y servicios */
    public Optional<Cliente> obtenerClienteCompleto(Integer id) {
        return clienteRepo.findById(id)
            .map(c -> {
                // opcional: cargar relaciones si están lazy
                c.getSolicitudes().size();
                c.getCalificaciones().size();
                return c;
            });
    }

    /** Actualizar datos de contacto */
    @Transactional
    public Cliente actualizarDatos(Integer id, Cliente nuevosDatos) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setTelefono(nuevosDatos.getTelefono());
        cliente.setDireccion(nuevosDatos.getDireccion());
        return clienteRepo.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }
}
