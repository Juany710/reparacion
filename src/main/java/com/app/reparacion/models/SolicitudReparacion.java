package com.app.reparacion.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitud_reparacion")
public class SolicitudReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitud_reparacion")
    private Integer idSolicitudReparacion;

    @Column(name = "Fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "Estado_Solicitud")
    private Boolean estadoSolicitud;

    @Column(name = "Detalle_solicitud", length = 100)
    private String detalleSolicitud;

    // 🔹 Relaciones
    @ManyToOne
    @JoinColumn(name = "cliente_usuario_idUsuario")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "categoria_idCategoria")
    private Categoria categoria;

    // 🔹 Getters y Setters
    public Integer getIdSolicitudReparacion() { 
        return idSolicitudReparacion; 
    }
    public void setIdSolicitudReparacion(Integer idSolicitudReparacion) { 
        this.idSolicitudReparacion = idSolicitudReparacion; 
    }

    public LocalDate getFechaSolicitud() { 
        return fechaSolicitud; 
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { 
        this.fechaSolicitud = fechaSolicitud; 
    }

    public Boolean getEstadoSolicitud() { 
        return estadoSolicitud; 
    }
    public void setEstadoSolicitud(Boolean estadoSolicitud) { 
        this.estadoSolicitud = estadoSolicitud; }


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
