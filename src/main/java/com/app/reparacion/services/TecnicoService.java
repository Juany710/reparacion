package com.app.reparacion.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.Categoria;
import com.app.reparacion.models.ServicioReparacion;
import com.app.reparacion.models.Tecnico;
import com.app.reparacion.repositories.CategoriaRepository;
import com.app.reparacion.repositories.ServicioReparacionRepository;
import com.app.reparacion.repositories.TecnicoRepository;

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
    public Tecnico actualizarCategorias(Integer idTecnico, List<Integer> idsCategorias) {
        Tecnico tecnico = tecnicoRepo.findById(idTecnico)
        .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        List<Categoria> categorias = categoriaRepo.findAllById(idsCategorias);
        tecnico.setCategorias(categorias);
        return tecnicoRepo.save(tecnico);
    }

}
