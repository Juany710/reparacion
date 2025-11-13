# 🚀 FixIt App - Guía Rápida de Ejecución

## Requisitos Previos

Asegúrate de tener instalado:
- **Java 21+** → `java -version`
- **Maven 3.8.9+** → `mvn -version`
- **Node.js 16+** → `node -v`
- **npm 8+** → `npm -v`
- **MySQL 8** → Debe estar corriendo (verificar en Servicios)

## Opción 1: Script Automático (Recomendado)

### Paso 1: Preparar base de datos
```powershell
# En MySQL Workbench o terminal MySQL
CREATE DATABASE reparacion_db CHARACTER SET utf8mb4;
```

### Paso 2: Ejecutar el script
```powershell
cd "C:\path\to\reparacion"
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope Process
.\run-app.ps1
```

El script:
- ✅ Detecta tu IP local automáticamente
- ✅ Inicia Backend (Spring Boot) en puerto 8080
- ✅ Espera a que Backend esté listo
- ✅ Inicia Frontend (Expo) en puerto 3000
- ✅ Configura `REACT_NATIVE_API_URL` automáticamente

### Paso 3: Abrir la app
- **Opción A (Móvil físico):**
  - Descarga "Expo Go" en tu teléfono
  - Escanea el QR que aparece en la terminal Expo
  - ¡App abierta en tu móvil!

- **Opción B (Emulador Android):**
  - Presiona `a` en la terminal Expo
  - Se abrirá automáticamente en Android Emulator

- **Opción C (Web - limitado):**
  - Presiona `w` en la terminal Expo
  - Se abrirá en `http://localhost:3000` en el navegador

---

## Opción 2: Manual (Terminal Separadas)

### Terminal 1: Backend
```powershell
cd "C:\path\to\reparacion"
mvn spring-boot:run
```
Verifica que veas:
```
Tomcat started on port(s): 8080 (http)
Started ReparacionApplication in X seconds
```

### Terminal 2: Frontend
```powershell
cd "C:\path\to\reparacion\fixit-mobile"
npm install
npm start
```
Verifica que veas el QR de Expo

---

## Variables de Entorno (Importante)

Antes de arrancar, configura estas claves en tu sistema:

### En PowerShell (temporal):
```powershell
$env:STRIPE_SECRET_KEY = "sk_test_..."
$env:FIREBASE_ADMIN_JSON = "path/to/firebase-admin.json"
$env:FIREBASE_PROJECT_ID = "tu-proyecto-id"
$env:GOOGLE_CLIENT_ID = "..."
$env:GOOGLE_CLIENT_SECRET = "..."
```

### O crear archivo `.env` en raíz del proyecto:
```bash
STRIPE_SECRET_KEY=sk_test_...
STRIPE_PUBLIC_KEY=pk_test_...
FIREBASE_ADMIN_JSON=/path/to/firebase-admin.json
FIREBASE_PROJECT_ID=tu-proyecto-id
GOOGLE_CLIENT_ID=...
GOOGLE_CLIENT_SECRET=...
```

Luego cargar en PowerShell (si lo necesitas):
```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match "^([^=]+)=(.*)$") {
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], "Process")
    }
}
```

---

## Cómo Probar Funcionalidades

### 1️⃣ Login / Registro
- Abre la app
- Toca "Crear Cuenta"
- Registra un usuario cliente o técnico
- Inicia sesión

### 2️⃣ GPS y Mapas (TecnicoHomeScreen)
- Inicia sesión como **Técnico**
- Abre "Inicio" (TecnicoHomeScreen)
- Acepta permisos de ubicación
- **Debes ver:**
  - Mapa con tu marcador azul (tu ubicación)
  - Lista de solicitudes con distancia en km
  - Botones "Aceptar" / "Rechazar"

### 3️⃣ Visualizar Solicitudes en Mapa (MisSolicitudesScreen)
- Inicia sesión como **Cliente**
- Abre "Mis Solicitudes"
- **Debes ver:**
  - Mapa mostrando ubicaciones de tus solicitudes
  - Lista de solicitudes con estado (Completado, En curso, Pendiente)
  - Al tocar una, el mapa se centra en esa ubicación

### 4️⃣ Pagos con Stripe (PantallaPagoScreen)
- Como Cliente, acepta una oferta en una solicitud
- Llega a la pantalla de pago
- **Usa tarjeta de prueba de Stripe:**
  ```
  Número:      4242 4242 4242 4242
  Fecha:       12/34
  CVC:         123
  Nombre:      Test Card
  ```
- Verifica en los logs del backend: `[STRIPE] PaymentIntent created`

### 5️⃣ Notificaciones (Firebase FCM)
- En Terminal 1 (logs del backend), busca: `Token registrado para usuario`
- Luego en Postman/curl, envía:
  ```bash
  POST http://localhost:8080/api/notificaciones/enviar
  
  {
    "deviceTokens": ["token_aqui"],
    "titulo": "¡Nueva solicitud!",
    "cuerpo": "Un cliente necesita tu ayuda"
  }
  ```
- La app debe mostrar la notificación (en Expo Go, se muestra como Alert)

---

## Troubleshooting

### ❌ "Backend no responde"
- Verifica que MySQL está corriendo
- Verifica que `spring.datasource.password` en `application.properties` es correcta
- En logs del backend, busca `Caused by:` para ver el error

### ❌ "Móvil no alcanza el backend"
**Solución 1:** Usa IP local (si estás en la misma red Wi-Fi)
```powershell
.\run-app.ps1 -IP 192.168.1.100
```

**Solución 2:** Usa Expo Tunnel (sin necesidad de estar en la misma red)
```powershell
npm start -- --tunnel
```

**Solución 3:** Usa 10.0.2.2 si usas Android Emulator en Windows
- En `api.js`, reemplaza `localhost` por `10.0.2.2`

### ❌ "Module not found: react-native-maps"
```powershell
cd fixit-mobile
npx expo install react-native-maps expo-location @expo/vector-icons
```

### ❌ "Compilation error en Java"
```powershell
cd "C:\path\to\reparacion"
mvn clean compile
```

---

## Puertos Usados

| Servicio | Puerto | URL |
|----------|--------|-----|
| Backend (Spring Boot) | 8080 | http://localhost:8080 |
| Frontend (Expo) | 3000 | http://localhost:3000 (web) |
| MySQL | 3306 | localhost:3306 |

---

## Comandos Útiles

```powershell
# Compilar backend sin tests
mvn clean compile -DskipTests

# Compilar + empaquetar
mvn clean package -DskipTests

# Ejecutar backend (sin Maven, con JAR)
java -jar target/reparacion-0.0.1-SNAPSHOT.jar

# Ver logs en tiempo real
mvn spring-boot:run -e

# Limpiar caché de Expo
expo cache clean

# Ver procesos en puerto
netstat -ano | findstr :8080

# Matar proceso en puerto
taskkill /PID <PID> /F
```

---

## Siguiente: Deploying en Producción

Una vez todo funciona en local:

1. **Backend:**
   - Cambia a una BD PostgreSQL o MySQL en la nube
   - Configura variables de entorno en el servidor
   - Deploy en Heroku, Railway, AWS, o tu servidor

2. **Frontend:**
   - Build para iOS/Android con EAS (Expo Application Services)
   - Sube a TestFlight (iOS) o Google Play (Android)
   - O publica web con `expo export --platform web`

---

**¿Alguna duda? Revisa los logs (terminal) y busca `[ERROR]` o `Exception`** 🔍
