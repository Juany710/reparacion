package com.app.reparacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ubicacion")
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbicacion;
    
    @NotNull
    private Double latitud;
    
    @NotNull
    private Double longitud;
    
    @NotNull
    private LocalDateTime timestamp;
    
    @ManyToOne
    @JoinColumn(name = "idtecnico")
    @NotNull
    private Tecnico tecnico;
    
    public Ubicacion() {}
    
    public Integer getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(Integer idUbicacion) { this.idUbicacion = idUbicacion; }
    
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
}
