package com.app.reparacion.services;

import com.app.reparacion.dto.PagoRequestDTO;
import com.app.reparacion.dto.PagoResponseDTO;
import com.app.reparacion.models.*;
import com.app.reparacion.models.enums.EstadoPago;
import com.app.reparacion.repositories.PagoRepository;
import com.app.reparacion.repositories.ClienteRepository;
import com.app.reparacion.repositories.OfertaRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PagoService {
    
    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private OfertaRepository ofertaRepository;
    
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    
    public PagoResponseDTO procesarPago(PagoRequestDTO request, Integer idCliente) throws Exception {
        Stripe.apiKey = stripeSecretKey;
        
        logger.info("Procesando pago para cliente: {}, monto: {}", idCliente, request.getMonto());
        
        try {
            Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> 
                    new IllegalArgumentException("Cliente no encontrado"));
            Oferta oferta = ofertaRepository.findById(request.getIdOferta()).orElseThrow(() -> 
                    new IllegalArgumentException("Oferta no encontrada"));
            
            long montoEnCentavos = Math.round(request.getMonto() * 100);
            
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(montoEnCentavos)
                    .setCurrency(request.getMoneda().toLowerCase())
                    .setPaymentMethod(request.getPaymentMethodId())
                    .setConfirm(true)
                    .setReturnUrl("https://tuapp.com/pago-confirmacion")
                    .putMetadata("cliente_id", idCliente.toString())
                    .putMetadata("oferta_id", request.getIdOferta().toString())
                    .build();
            
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            
            return guardarPago(paymentIntent, request, idCliente, cliente, oferta);
            
        } catch (StripeException e) {
            logger.error("Error en Stripe: {} - {}", e.getCode(), e.getMessage());
            return crearPagoFallido(request, idCliente, e.getCode(), e.getMessage());
        }
    }
    
    private PagoResponseDTO guardarPago(PaymentIntent paymentIntent, 
                                        PagoRequestDTO request, 
                                        Integer idCliente,
                                        Cliente cliente,
                                        Oferta oferta) {
        Pago pago = new Pago();
        pago.setMonto(request.getMonto());
        pago.setReferencia_stripe(paymentIntent.getId());
        pago.setMetodo(request.getMetodo());
        pago.setMoneda(request.getMoneda());
        pago.setFecha(LocalDateTime.now());
        pago.setCliente(cliente);
        pago.setOferta(oferta);
        
        switch (paymentIntent.getStatus()) {
            case "succeeded":
                pago.setEstado(EstadoPago.COMPLETADO);
                logger.info("Pago completado: {}", paymentIntent.getId());
                break;
            case "processing":
                pago.setEstado(EstadoPago.PENDIENTE);
                logger.info("Pago en proceso: {}", paymentIntent.getId());
                break;
            case "requires_action":
                pago.setEstado(EstadoPago.PENDIENTE);
                logger.info("Pago requiere confirmación adicional: {}", paymentIntent.getId());
                break;
            case "requires_payment_method":
                pago.setEstado(EstadoPago.FALLIDO);
                pago.setError_code("METHOD_REQUIRED");
                pago.setError_mensaje("Se requiere un método de pago válido");
                logger.warn("Pago fallido - método requerido: {}", paymentIntent.getId());
                break;
            default:
                pago.setEstado(EstadoPago.FALLIDO);
                pago.setError_code("UNKNOWN_STATUS");
                pago.setError_mensaje("Estado desconocido: " + paymentIntent.getStatus());
                logger.warn("Estado desconocido: {}", paymentIntent.getStatus());
        }
        
        Pago pagoGuardado = pagoRepository.save(pago);
        return convertirAPagoResponseDTO(pagoGuardado);
    }
    
    private PagoResponseDTO crearPagoFallido(PagoRequestDTO request, Integer idCliente, String codigo, String mensaje) {
        Pago pago = new Pago();
        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo());
        pago.setMoneda(request.getMoneda());
        pago.setEstado(EstadoPago.FALLIDO);
        pago.setError_code(codigo);
        pago.setError_mensaje(mensaje);
        pago.setFecha(LocalDateTime.now());
        pago.setReferencia_stripe("ERROR_SIN_REFERENCIA");
        
        Pago pagoGuardado = pagoRepository.save(pago);
        return convertirAPagoResponseDTO(pagoGuardado);
    }
    
    public List<Pago> obtenerHistorialPagosCliente(Integer idCliente) {
        // El repositorio ahora usa cliente.idUsuario
        return pagoRepository.findPagosClientePorFecha(idCliente);
    }
    
    public Optional<Pago> obtenerPagoPorId(Integer idPago) {
        return pagoRepository.findById(idPago);
    }
    
    public Optional<Pago> obtenerPagoPorReferencia(String referencia) {
        return pagoRepository.findByReferencia_stripe(referencia);
    }
    
    public List<Pago> obtenerPagosPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado);
    }
    
    public void reembolsar(Integer idPago) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        
        Optional<Pago> pago = pagoRepository.findById(idPago);
        if (!pago.isPresent()) {
            throw new IllegalArgumentException("Pago no encontrado");
        }
        
        Pago pagoExistente = pago.get();
        if (!pagoExistente.getEstado().equals(EstadoPago.COMPLETADO)) {
            throw new IllegalStateException("Solo se pueden reembolsar pagos completados");
        }
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("payment_intent", pagoExistente.getReferencia_stripe());
            
            com.stripe.model.Refund refund = com.stripe.model.Refund.create(params);

            // Persistir estado de reembolso y registrar en logs el id del reembolso
            pagoExistente.setEstado(EstadoPago.REEMBOLSADO);
            pagoRepository.save(pagoExistente);

            logger.info("Reembolso procesado para pago: {} ; refundId={}", idPago, refund.getId());
        } catch (StripeException e) {
            logger.error("Error al reembolsar: {}", e.getMessage());
            throw e;
        }
    }
    
    public PagoResponseDTO convertirAPagoResponseDTO(Pago pago) {
        return new PagoResponseDTO(
                pago.getIdPago(),
                pago.getMonto(),
                pago.getEstado(),
                pago.getReferencia_stripe(),
                pago.getMetodo(),
                pago.getError_code(),
                pago.getError_mensaje(),
                pago.getFecha(),
                pago.getMoneda()
        );
    }
}
