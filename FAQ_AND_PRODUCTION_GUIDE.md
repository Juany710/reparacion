# FAQ: Preguntas Frecuentes sobre APIs Externas

## 1. PAGOS CON STRIPE

### P: ¿Qué pasa si el cliente ingresa una tarjeta falsa?
**R:** Stripe valida la tarjeta antes de cobrar. Si es falsa, rechaza el pago. Tu backend registra el error con código (ej. `card_declined`).

### P: ¿Puedo guardar la tarjeta para cobros futuros?
**R:** **SÍ.** Stripe permite tokenizar tarjetas. Luego usas el `paymentMethodId` sin que vuelva a ingresar la tarjeta.

Flujo mejorado:
```javascript
// Primer pago: usuario ingresa tarjeta
const paymentMethod = await stripe.createPaymentMethod({ card });
const paymentMethodId = paymentMethod.id;

// Guardar en BD: Pago { paymentMethodId, cliente, ... }

// Pagos futuros: reutilizar
const response = await pagoService.procesarPago(paymentMethodId, ...);
```

### P: ¿Qué pasa si no verifico a tiempo si el pago fue exitoso?
**R:** Usa **Webhooks de Stripe**. Stripe te notifica automáticamente cuando un pago se completa/falla:

```java
// Backend: recibe evento de Stripe
@PostMapping("/webhook/stripe")
public ResponseEntity<?> handleStripeWebhook(@RequestBody String body) {
    // Verificar firma del webhook (seguridad)
    // Event puede ser: payment_intent.succeeded, payment_intent.payment_failed
    // Actualizar BD con estado real
}
```

### P: ¿Stripe cobra si el pago falla?
**R:** **NO.** Stripe **solo cobra si el pago es exitoso**. Sin éxito = sin costo.

### P: ¿Puedo reembolsar dinero?
**R:** **SÍ.** Via API:

```java
refund = Refund.create(mapOf(
    "charge", "ch_xxxx"
));
// Refund estará pendiente 5-10 días. Dinero regresa a cliente.
```

### P: ¿Y si el cliente hace 1000 intentos de pago fallidos?
**R:** Stripe intenta solo 3 veces. Además, tú puedes limitar en frontend:

```javascript
const [intentos, setIntentos] = useState(0);

if (intentos > 2) {
  return <Text>Máximo 3 intentos. Intenta más tarde.</Text>;
}
```

---

## 2. GOOGLE MAPS

### P: ¿Necesito una tarjeta de crédito para usar Google Maps gratis?
**R:** **SÍ.** Google Cloud requiere tarjeta para activar APIs (pero puedes usar free tier hasta $200/mes).

### P: ¿Puedo usar mapas offline?
**R:** **NO con Google Maps**. Pero puedes:
- Descargar mapa offline con `react-native-maps` + OpenStreetMap.
- O guardar ubicaciones en caché (BD local) y mostrar offline.

### P: ¿Funciona en China?
**R:** **NO.** Google Maps está bloqueado en China. Necesitarías Amap o Baidu Maps (APIs chinas locales).

### P: ¿Cómo obtengo la ruta real (no línea recta) entre dos puntos?
**R:** Necesitas Google Maps **Directions API** (costo aparte):

```java
// Backend
String url = "https://maps.googleapis.com/maps/api/directions/json" +
    "?origin=" + latitud1 + "," + longitud1 +
    "&destination=" + latitud2 + "," + longitud2 +
    "&key=" + GOOGLE_MAPS_API_KEY;

RestTemplate rest = new RestTemplate();
String response = rest.getForObject(url, String.class);
// Parsear: ruta real, distancia, duración
```

### P: ¿Cómo obtengo ETA realista (tráfico incluido)?
**R:** Google Directions API calcula automáticamente con tráfico real si usas `departure_time=now`.

### P: ¿Puedo rastrear varios técnicos en el mismo mapa?
**R:** **SÍ.** Simplemente renderiza múltiples `<Marker>` en MapView:

```javascript
<MapView>
  {ubicaciones.map((ubi) => (
    <Marker
      key={ubi.tecnicoId}
      coordinate={{ latitude: ubi.lat, longitude: ubi.lng }}
      title={`Técnico ${ubi.nombre}`}
    />
  ))}
</MapView>
```

---

## 3. FIREBASE CLOUD MESSAGING

### P: ¿Cómo sé que la notificación fue recibida?
**R:** Firebase no confirma entrega. Pero puedes:
1. Registrar un evento en cliente: `usuario vio notificación`
2. Backend escucha: `POST /api/notificaciones/{id}/visto`

Alternativa: usar **Firebase Analytics** (gratis, integrado).

### P: ¿Puedo enviar notificaciones a múltiples dispositivos?
**R:** **SÍ**, de 3 formas:

1. **Tópicos** (recomendado para grupos):
```java
// Cliente se suscribe a tópico
messaging().subscribeToTopic("reparaciones-electricas");

// Backend envía a todos en tópico
fcm.send(Message.builder()
    .setTopic("reparaciones-electricas")
    .setNotification(...)
    .build());
```

2. **Multicast** (hasta 500 dispositivos):
```java
fcm.sendMulticast(Message.builder()
    .setTokens(Arrays.asList("token1", "token2", ...))
    .setNotification(...)
    .build());
```

3. **Individual** (un dispositivo):
```java
fcm.send(Message.builder()
    .setToken("device_token")
    .setNotification(...)
    .build());
```

### P: ¿Puedo enviar datos custom en la notificación?
**R:** **SÍ**, con el campo `data`:

```java
fcm.send(Message.builder()
    .setToken(deviceToken)
    .setNotification(Notification.builder()
        .setTitle("Nueva solicitud")
        .setBody("En tu zona")
        .build())
    .putData("solicitudId", "123")
    .putData("direccion", "Calle Principal 45")
    .build());
```

En cliente:
```javascript
messaging().onMessage((message) => {
  const solicitudId = message.data.solicitudId;
  // Navegar a pantalla de solicitud
  navigation.navigate('Solicitud', { id: solicitudId });
});
```

### P: ¿Las notificaciones funcionan sin internet?
**R:** **NO.** Necesitan conexión. Pero Firebase guarda en cola y entrega cuando haya conectividad.

### P: ¿Cuánto tarda en llegar una notificación?
**R:** Típicamente **< 5 segundos** si la app está abierta, **< 1 minuto** en background.

---

## 4. GENERAL

### P: ¿Cuál es el costo total de usar todas las APIs?
**R:** Para 10,000 reparaciones/año a €30 cada una:

| Servicio | Costo Anual |
|----------|------------|
| Stripe (2.9% + $0.30) | €8,700 |
| Google Maps | €525 |
| Firebase | €0 |
| SendGrid | €1 |
| Twilio (SMS) | €700 |
| **TOTAL** | **€9,926** |

**Como % de ingresos:** 9,926 / 300,000 = **3.3%** (muy barato)

---

### P: ¿Qué pasa si una API externa se cae?
**R:** Depende de la API:

- **Stripe se cae:** Tu app no puede cobrar. Usuarios ven error. Puedes mostrar mensaje: "Intenta más tarde".
- **Google Maps se cae:** Mapa no carga. Pero tienes ubicación GPS en BD. Puedes usar OpenStreetMap como fallback.
- **Firebase se cae:** Notificaciones no llegan. Usuario se enterea cuando abre app. Poco crítico.

**Solución:** implementar **circuit breaker** en tu backend:

```java
@Service
public class PagoService {
    private int stripeFailures = 0;
    
    public boolean isStripeAvailable() {
        return stripeFailures < 3; // Si falla 3 veces, deshabilita Stripe
    }
    
    public PagoResponseDTO procesarPago(...) {
        if (!isStripeAvailable()) {
            throw new ServiceUnavailableException("Pagos temporalmente deshabilitados");
        }
        // ... procesar pago
    }
}
```

---

### P: ¿Cómo manejo errores de APIs externas?
**R:** Siempre:
1. **Intenta la llamada**
2. **Captura excepciones**
3. **Registra el error** (logging)
4. **Notifica al usuario** (mensaje amigable)
5. **Reintenta después** (retry logic con exponential backoff)

```java
public PagoResponseDTO procesarPago(...) {
    int intentos = 0;
    int maxIntentos = 3;
    
    while (intentos < maxIntentos) {
        try {
            return stripe.confirm(...);
        } catch (StripeException e) {
            intentos++;
            if (intentos < maxIntentos) {
                // Esperar antes de reintentar: 2^intentos segundos
                Thread.sleep(Math.pow(2, intentos) * 1000);
            } else {
                // Guardar error y fallar
                pago.setEstado(EstadoPago.FALLIDO);
                pago.setError_code(e.getCode());
                pagoRepository.save(pago);
                throw e;
            }
        }
    }
}
```

---

### P: ¿Necesito HTTPS para usar APIs externas?
**R:** **SÍ, obligatorio.**

- Stripe **rechaza** requests sin HTTPS.
- Google Maps **rechaza** requests sin HTTPS.
- Firebase **rechaza** requests sin HTTPS.

En desarrollo (localhost): **HTTP OK**.
En producción: **HTTPS OBLIGATORIO** (Let's Encrypt = gratis).

---

### P: ¿Cómo obtengo credenciales en producción?
**R:**

| API | Test | Producción |
|-----|------|-----------|
| **Stripe** | `pk_test_`, `sk_test_` | `pk_live_`, `sk_live_` (requiere verificación) |
| **Google Maps** | API Key genérico | API Key con restricciones (por dominio/IP) |
| **Firebase** | Proyecto "test" | Proyecto "producción" |

Flujo:
```properties
# development (local)
stripe.secret.key=sk_test_123...

# staging
stripe.secret.key=sk_test_456... (otra clave test)

# production
stripe.secret.key=sk_live_789... (DESPUÉS de verificar identidad)
```

---

### P: ¿Cómo protejo mis API keys?
**R:** **NUNCA** las comitas a Git:

```bash
# 1. Crear .env.local (no versionado)
echo "STRIPE_SECRET_KEY=sk_live_xxx" > .env.local

# 2. En .gitignore
echo ".env.local" >> .gitignore

# 3. En aplicación, leer de variable de entorno
@Value("${stripe.secret.key}")
private String stripeSecretKey;

# 4. En servidor de producción, setear variable
export STRIPE_SECRET_KEY=sk_live_xxx
```

---

### P: ¿Cuál es la diferencia entre test y producción en APIs?
**R:**

| Aspecto | Test | Producción |
|--------|------|-----------|
| **Claves** | `pk_test_`, `sk_test_` | `pk_live_`, `sk_live_` |
| **Dinero real** | NO (simulado) | **SÍ** (cobrado a clientes) |
| **Documentación** | Tarjetas fake (4242...) | Tarjetas reales |
| **Límites** | Sin límites | Depende de plan |
| **Soporte** | Comunidad | Soporte dedicado |

**Importante:** Antes de ir a producción:
1. Prueba pagos con tarjetas test
2. Prueba webhooks
3. Prueba reembolsos
4. Prueba errores (tarjetas rechazadas)
5. Verifica logs
6. **Activa claves live en Stripe**
7. Actualiza configuración (`sk_live_...`)

---

### P: ¿Qué pasa con los datos después de un pago?
**R:** En BD guardas:
- `monto` (qué cobró)
- `referencia_stripe` (para tracking y reembolsos)
- `fecha` (cuándo)
- `estado` (COMPLETADO/FALLIDO)
- `cliente_id`, `oferta_id` (quién y por qué)

**NO guardas:**
- Número de tarjeta (nunca toca tu servidor)
- CVC (nunca toca tu servidor)
- Tokens de Stripe (opcional: guardar solo si quieres reutilizar)

---

### P: ¿Y las auditorías/compliance?
**R:** Debes mantener logs de:
1. Quién intentó pagar
2. Cuándo
3. Monto
4. Resultado (exitoso/fallido)
5. Errores si existen

```sql
-- Tabla de auditoría
CREATE TABLE auditoria_pago (
    id INT PRIMARY KEY,
    cliente_id INT,
    monto DECIMAL,
    fecha TIMESTAMP,
    estado VARCHAR(20),
    error_code VARCHAR(100),
    ip_usuario VARCHAR(50),
    user_agent VARCHAR(500)
);
```

---

## RESUMEN DE RECOMENDACIONES

### ✅ ANTES DE LANZAR A PRODUCCIÓN

- [ ] Prueba pagos con tarjetas fake (Stripe sandbox)
- [ ] Prueba reembolsos
- [ ] Prueba webhooks (eventos de Stripe)
- [ ] Implementa logging detallado
- [ ] Implementa retry logic (reintento con backoff)
- [ ] Implementa circuit breaker (protección si API cae)
- [ ] Configura HTTPS (Let's Encrypt)
- [ ] Guarda API keys en variables de entorno
- [ ] Prueba mapas en múltiples dispositivos
- [ ] Prueba notificaciones en foreground y background
- [ ] Registra errores en observability tool (Datadog, Sentry, etc.)
- [ ] Crea runbook para emergencias (qué hacer si X API se cae)

### ✅ MONITOREO CONTINUO

- [ ] Alerta si Stripe rechaza > 5% pagos
- [ ] Alerta si Google Maps requests > threshold
- [ ] Alerta si Firebase notifications fallan
- [ ] Dashboard con métricas: pagos/día, tasa éxito, errores

---

**Fin del FAQ**
