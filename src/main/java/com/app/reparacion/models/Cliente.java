package com.app.reparacion.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cliente")
    @Valid
    private List<SolicitudReparacion> solicitudes;

    // 🔹 Getters y Setters
    public List<SolicitudReparacion> getSolicitudes() { 
        return solicitudes; 
    }
    public void setSolicitudes(List<SolicitudReparacion> solicitudes) { 
        this.solicitudes = solicitudes; 
    }
}
