package com.app.reparacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "device_token")
public class DeviceToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeviceToken;
    
    @NotNull
    @Column(length = 500)
    private String token;
    
    @NotNull
    @Column(length = 45)
    private String plataforma;
    
    @NotNull
    private LocalDateTime fecha_registro;
    
    @ManyToOne
    @JoinColumn(name = "idusuario")
    @NotNull
    @JsonIgnoreProperties("deviceTokens")
    private Usuario usuario;
    
    public DeviceToken() {}
    
    public Integer getIdDeviceToken() { return idDeviceToken; }
    public void setIdDeviceToken(Integer idDeviceToken) { this.idDeviceToken = idDeviceToken; }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    
    public LocalDateTime getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
