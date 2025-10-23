package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.reparacion.models.Cliente;
import com.app.reparacion.repositories.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
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
            // Forzar la carga de relaciones si están en modo LAZY
            c.getSolicitudes().size();
            c.getCalificacionesRecibidas().size(); // 👈 carga las que recibió
            c.getCalificacionesEmitidas().size();  // 👈 carga las que envió
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

    @Transactional
    public void eliminarCliente(Integer id) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    if (!cliente.getSolicitudes().isEmpty()) {
        throw new IllegalStateException("No se puede eliminar un cliente con solicitudes activas");
    }

    clienteRepo.delete(cliente);
}

    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }
}
