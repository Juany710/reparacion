package com.app.reparacion.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.reparacion.models.Calificacion;
import com.app.reparacion.repositories.CalificacionRepository;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepo;

    public CalificacionService(CalificacionRepository calificacionRepo) {
        this.calificacionRepo = calificacionRepo;
    }

    public Calificacion registrar(Calificacion calificacion) {
        return calificacionRepo.save(calificacion);
    }

    public List<Calificacion> listarPorDestinatario(Integer idUsuario) {
        return calificacionRepo.findByDestinatarioId(idUsuario);
    }

    public Double promedioPorTecnico(Integer idTecnico) {
        return calificacionRepo.promedioPorDestinatario(idTecnico);
    }
}
