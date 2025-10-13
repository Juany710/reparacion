package com.app.reparacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
    @Column(name = "Nombre", nullable = false, length = 45)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 45, message = "El apellido no puede superar los 45 caracteres")
    @Column(name = "Apellido", nullable = false, length = 45)
    private String apellido;

    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 1000000, message = "El DNI debe tener al menos 7 dígitos")
    @Column(name = "Dni", nullable = false, unique = true)
    private Integer dni;

    @Size(max = 45, message = "La dirección no puede superar los 45 caracteres")
    @Column(name = "Direccion", length = 45)
    private String direccion;

    @DecimalMin(value = "0.0", message = "El promedio no puede ser negativo")
    @DecimalMax(value = "5.0", message = "El promedio no puede superar 5.0")
    @Column(name = "promedio_calificacion")
    private Double promedioCalificacion;

    // 🔹 Getters y Setters
    public Integer getIdUsuario() { 
        return idUsuario; 
    }
    public void setIdUsuario(Integer idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getApellido() { 
        return apellido; 
    }
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

    public Integer getDni() { 
        return dni; 
    }
    public void setDni(Integer dni) { 
        this.dni = dni; 
    }

    public String getDireccion() { 
        return direccion; 
    }
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }

        public Double getPromedioCalificacion() {
        return promedioCalificacion;
    }
    public void setPromedioCalificacion(Double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }
}