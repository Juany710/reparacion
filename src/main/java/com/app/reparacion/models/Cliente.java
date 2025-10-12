package com.app.reparacion.models;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{
    
@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<SolicitudReparacion> solicitudes;

    // 🔹 Getters y Setters
    public List<SolicitudReparacion> getSolicitudes() { 
        return solicitudes; 
    }
    public void setSolicitudes(List<SolicitudReparacion> solicitudes) { 
        this.solicitudes = solicitudes; 
    }
}
