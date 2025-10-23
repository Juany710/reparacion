package com.app.reparacion.models;

import java.time.LocalDate;
import java.util.List;
import com.app.reparacion.models.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "solicitud_reparacion")
public class SolicitudReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSolicitud;

    @PastOrPresent(message = "La fecha de solicitud no puede ser futura")
    @Column(name = "Fecha_solicitud")
    private LocalDate fechaSolicitud;

    @NotNull(message = "El estado de la solicitud es obligatorio")
    @Column(name = "Estado_Solicitud")
    private Estado estado;

    @NotBlank(message = "El detalle de la solicitud es obligatorio")
    @Column(name = "Detalle_solicitud", length = 100)
    private String detalleSolicitud;

    // 🔹 Relaciones
    @ManyToOne
    @JoinColumn(name = "cliente_usuario_idUsuario")
    @NotNull(message = "Debe estar asociada a un cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "categoria_idCategoria")
    @NotNull(message = "Debe seleccionar una categoría")
    private Categoria categoria;

    @OneToMany(mappedBy = "solicitud")
    @JsonIgnoreProperties("solicitud")
    private List<Oferta> ofertas;

    // 🔹 Getters y Setters
    public Integer getIdSolicitudReparacion() { 
        return idSolicitud; 
    }
    public void setIdSolicitudReparacion(Integer idSolicitudReparacion) { 
        this.idSolicitud = idSolicitudReparacion; 
    }

    public LocalDate getFechaSolicitud() { 
        return fechaSolicitud; 
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { 
        this.fechaSolicitud = fechaSolicitud; 
    }

    public Estado getEstado() { 
        return estado; 
    }
    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }

    public String getDetalleSolicitud() { 
        return detalleSolicitud; 
    }
    public void setDetalleSolicitud(String detalleSolicitud) { 
        this.detalleSolicitud = detalleSolicitud; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }

    public Categoria getCategoria() { 
        return categoria; 
    }
    public void setCategoria(Categoria categoria) { 
        this.categoria = categoria; 
    }
}
