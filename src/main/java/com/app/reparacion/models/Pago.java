package com.app.reparacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.app.reparacion.models.enums.EstadoPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;
    
    @NotNull
    @Positive
    private Double monto;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoPago estado;
    
    @NotNull
    @Column(length = 255)
    private String referencia_stripe;
    
    @Column(length = 45)
    private String metodo;
    
    @Column(length = 255)
    private String error_code;
    
    @Column(length = 500)
    private String error_mensaje;
    
    @PastOrPresent
    @NotNull
    private LocalDateTime fecha;
    
    @NotNull
    @Column(length = 10)
    private String moneda;
    
    @ManyToOne
    @JoinColumn(name = "idoferta")
    @NotNull
    private Oferta oferta;
    
    @ManyToOne
    @JoinColumn(name = "idservicio")
    private ServicioReparacion servicio;
    
    @ManyToOne
    @JoinColumn(name = "idcliente")
    @NotNull
    @JsonIgnoreProperties("pagos")
    private Cliente cliente;
    
    public Pago() {}
    
    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
    
    public String getReferencia_stripe() { return referencia_stripe; }
    public void setReferencia_stripe(String referencia_stripe) { this.referencia_stripe = referencia_stripe; }
    
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    
    public String getError_code() { return error_code; }
    public void setError_code(String error_code) { this.error_code = error_code; }
    
    public String getError_mensaje() { return error_mensaje; }
    public void setError_mensaje(String error_mensaje) { this.error_mensaje = error_mensaje; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    
    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }
    
    public ServicioReparacion getServicio() { return servicio; }
    public void setServicio(ServicioReparacion servicio) { this.servicio = servicio; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
