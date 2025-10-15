package com.app.reparacion.models;

import java.time.LocalDate;
import java.util.List;
import com.app.reparacion.models.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    private String telefono;

    private List<Calificacion> calificaciones;

    @DecimalMin(value = "0.0", message = "El promedio no puede ser negativo")
    @DecimalMax(value = "5.0", message = "El promedio no puede superar 5.0")
    @Column(name = "promedio_calificacion")
    private Double promedioCalificacion;

     //Para login
    @Column(unique = true, nullable = false)
    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;   // o DNI como identificador de login

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "El DNI o username no puede estar vacío")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password; // encriptada con BCrypt

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol; // ADMIN, CLIENTE, TECNICO, etc.

    @Column(nullable = false)
    @PastOrPresent(message = "La fecha de registro no puede ser futura")
    private LocalDate fechaRegistro;

//Agregar tablas de login a la base de datos

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

        public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }
    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public Double getPromedioCalificacion() {
        return promedioCalificacion;
    }
    public void setPromedioCalificacion(Double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}