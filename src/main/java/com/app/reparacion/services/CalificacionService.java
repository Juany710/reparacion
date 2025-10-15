package com.app.reparacion.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app.reparacion.models.Calificacion;
import com.app.reparacion.models.Usuario;
import com.app.reparacion.repositories.CalificacionRepository;
import com.app.reparacion.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepo;
    private final UsuarioRepository usuarioRepo;

    public CalificacionService(CalificacionRepository calificacionRepo, UsuarioRepository usuarioRepo) {
        this.calificacionRepo = calificacionRepo;
        this.usuarioRepo = usuarioRepo;
    }

   /** Registrar una nueva calificación y actualizar promedio del usuario destinatario */
    @Transactional
    public Calificacion registrarCalificacion(Calificacion calificacion) {
        // 1️⃣ Guardar la nueva calificación
        Calificacion nueva = calificacionRepo.save(calificacion);

        // 2️⃣ Obtener el usuario destinatario
        Usuario destinatario = calificacion.getDestinatario();

        // 3️⃣ Calcular su nuevo promedio
        Double nuevoPromedio = calcularPromedioPorDestinatario(destinatario.getIdUsuario());

        // 4️⃣ Actualizar el promedio en el usuario
        destinatario.setPromedioCalificacion(nuevoPromedio);
        usuarioRepo.save(destinatario);

        // 5️⃣ Retornar la calificación guardada
        return nueva;
    }

    /** Calcular promedio de calificaciones recibidas por un usuario */
    public Double calcularPromedioPorDestinatario(Integer idDestinatario) {
        List<Calificacion> calificaciones = calificacionRepo.findByDestinatario_IdUsuario(idDestinatario);

        if (calificaciones.isEmpty()) return 0.0;

        double suma = calificaciones.stream()
                .mapToDouble(Calificacion::getPuntaje)
                .sum();

        return suma / calificaciones.size();
    }

    public List<Calificacion> listarPorDestinatario(Integer idUsuario) {
        return calificacionRepo.findByDestinatario_IdUsuario(idUsuario);
    }
}
