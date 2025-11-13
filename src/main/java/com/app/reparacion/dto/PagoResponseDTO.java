package com.app.reparacion.dto;

import com.app.reparacion.models.enums.EstadoPago;
import java.time.LocalDateTime;

public class PagoResponseDTO {
    
    private Integer idPago;
    private Double monto;
    private EstadoPago estado;
    private String referencia_stripe;
    private String metodo;
    private String error_code;
    private String error_mensaje;
    private LocalDateTime fecha;
    private String moneda;
    
    public PagoResponseDTO() {}
    
    public PagoResponseDTO(Integer idPago, Double monto, EstadoPago estado, 
                          String referencia_stripe, String metodo, 
                          String error_code, String error_mensaje, 
                          LocalDateTime fecha, String moneda) {
        this.idPago = idPago;
        this.monto = monto;
        this.estado = estado;
        this.referencia_stripe = referencia_stripe;
        this.metodo = metodo;
        this.error_code = error_code;
        this.error_mensaje = error_mensaje;
        this.fecha = fecha;
        this.moneda = moneda;
    }
    
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
}
