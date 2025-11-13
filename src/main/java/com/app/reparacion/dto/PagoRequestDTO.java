package com.app.reparacion.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

public class PagoRequestDTO {
    
    @NotNull(message = "El ID de oferta es requerido")
    private Integer idOferta;
    
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    @NotNull(message = "El método de pago es requerido")
    @Pattern(regexp = "TARJETA|TRANSFERENCIA", message = "Método no válido")
    private String metodo;
    
    @NotNull(message = "El payment method Stripe es requerido")
    private String paymentMethodId;
    
    @NotNull(message = "La moneda es requerida")
    @Pattern(regexp = "EUR|USD", message = "Moneda no soportada")
    private String moneda;
    
    public PagoRequestDTO() {}
    
    public Integer getIdOferta() { return idOferta; }
    public void setIdOferta(Integer idOferta) { this.idOferta = idOferta; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    
    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }
    
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}
