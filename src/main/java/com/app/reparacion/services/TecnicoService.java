package com.app.reparacion.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.Categoria;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.Tecnico;
import com.app.reparacion.repositories.CategoriaRepository;
import com.app.reparacion.repositories.ServicioReparacionRepository;
import com.app.reparacion.repositories.TecnicoRepository;
import jakarta.transaction.Transactional;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepo;
    private final ServicioReparacionRepository servicioRepo;
    private final CategoriaRepository categoriaRepo;

    public TecnicoService(TecnicoRepository tecnicoRepo, ServicioReparacionRepository servicioRepo, CategoriaRepository categoriaRepo) {
        this.tecnicoRepo = tecnicoRepo;
        this.servicioRepo = servicioRepo;
        this.categoriaRepo = categoriaRepo;
    }

    /** Registrar nuevo técnico con validaciones */
    @Transactional
    public Tecnico registrarTecnico(Tecnico tecnico) {
        if (tecnicoRepo.existsByEmail(tecnico.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (tecnicoRepo.existsByUsername(tecnico.getUsername())) {
            throw new IllegalArgumentException("El DNI o nombre de usuario ya está registrado");
        }
        tecnico.setFechaRegistro(LocalDate.now());
        return tecnicoRepo.save(tecnico);
    }

    /** Obtener técnico con historial de servicios y categorías */
    public Optional<Tecnico> obtenerTecnicoCompleto(Integer id) {
        return tecnicoRepo.findById(id)
                .map(t -> {
                    //Cargar relaciones si están lazy
                    t.getServicios().size();    // historial de servicios
                    t.getCategorias().size();   // especialidades o rubros
                    t.getCalificaciones().size();
                    return t;
                });
    }

    /** Actualizar datos de contacto del técnico */
    @Transactional
    public Tecnico actualizarDatos(Integer id, Tecnico nuevosDatos) {
        Tecnico tecnico = tecnicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        tecnico.setTelefono(nuevosDatos.getTelefono());
        tecnico.setDireccion(nuevosDatos.getDireccion());
        tecnico.setEmail(nuevosDatos.getEmail());

        return tecnicoRepo.save(tecnico);
    }

    /** Listar todos los técnicos */
    public List<Tecnico> listarTecnicos() {
        return tecnicoRepo.findAll();
    }

    /** Historial de servicios realizados por el técnico */
    public List<ServicioReparacion> historialServicios(Integer idTecnico) {
        return servicioRepo.findByOfertaTecnicoId(idTecnico);
    }

    /** Categorías actuales del técnico */
    public List<Categoria> listarCategorias(Integer idTecnico) {
        Tecnico tecnico = tecnicoRepo.findById(idTecnico)
        .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        return tecnico.getCategorias();
    } 

    /** Actualizar categorías del técnico */
    @Transactional
    public Tecnico actualizarCategorias(Integer idTecnico, List<Integer> idsCategorias) {
        Tecnico tecnico = tecnicoRepo.findById(idTecnico)
        .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        List<Categoria> categorias = categoriaRepo.findAllById(idsCategorias);
        tecnico.setCategorias(categorias);
        return tecnicoRepo.save(tecnico);
        //se podria agregar una validacion antes de asignar categoria para evitar duplicados
    }

}
