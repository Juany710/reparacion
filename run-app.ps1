# ============================================================================
# Script para arrancar FixIt App (Backend + Frontend)
# Uso: .\run-app.ps1 o .\run-app.ps1 -IP 192.168.x.y
# ============================================================================

param(
    [string]$IP = "localhost",
    [int]$BackendPort = 8080,
    [int]$FrontendPort = 8081
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   FixIt App - Multi-Platform" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Detectar IP local si no se especifica
if ($IP -eq "localhost") {
    $localIP = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object { $_.IPAddress -match "^192\.168" } | Select-Object -First 1).IPAddress
    if ($localIP) {
        $IP = $localIP
        Write-Host "[INFO] IP local detectada: $IP" -ForegroundColor Yellow
    } else {
        Write-Host "[WARN] No se detecto IP local. Usando localhost (solo funciona en dispositivo local)" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "Configuración:" -ForegroundColor Cyan
Write-Host "  Backend:  http://${IP}:$BackendPort" -ForegroundColor White
Write-Host "  Frontend: http://${IP}:$FrontendPort" -ForegroundColor White
Write-Host ""

# Verificar si MySQL está corriendo
Write-Host "Verificando MySQL..." -ForegroundColor Cyan
$mysqlRunning = Get-Process mysqld -ErrorAction SilentlyContinue
if (-not $mysqlRunning) {
    Write-Host "[WARN] MySQL no esta corriendo. Asegurate de tenerlo iniciado." -ForegroundColor Yellow
    Write-Host "   En Windows: Services > MySQL80 (o tu version)" -ForegroundColor Gray
    Read-Host "   Presiona Enter cuando MySQL esté listo"
}
Write-Host "[OK] MySQL OK" -ForegroundColor Green
Write-Host ""

# Paths
$backendPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$frontendPath = Join-Path $backendPath "fixit-mobile"

# Verificar que existan los directorios
if (-not (Test-Path $backendPath)) {
    Write-Host "❌ Backend path no encontrado: $backendPath" -ForegroundColor Red
    exit 1
}
if (-not (Test-Path $frontendPath)) {
    Write-Host "❌ Frontend path no encontrado: $frontendPath" -ForegroundColor Red
    exit 1
}

Write-Host "Directorios:" -ForegroundColor Cyan
Write-Host "  Backend:  $backendPath" -ForegroundColor Gray
Write-Host "  Frontend: $frontendPath" -ForegroundColor Gray
Write-Host ""

# Iniciar backend en segundo plano (proceso separado)
Write-Host "Iniciando Backend (Spring Boot)..." -ForegroundColor Cyan
Write-Host "  Puerto: $BackendPort" -ForegroundColor Gray

# Comando backend preferente: mvnw.cmd si existe, sino mvn, sino jar
$backendCommand = $null
$backendArgs = $null
if (Test-Path (Join-Path $backendPath "mvnw.cmd")) {
    $backendCommand = (Join-Path $backendPath "mvnw.cmd")
    $backendArgs = @("spring-boot:run", "-Dspring-boot.run.arguments=--server.port=$BackendPort")
} elseif (Get-Command mvn -ErrorAction SilentlyContinue) {
    $backendCommand = "mvn"
    $backendArgs = @("spring-boot:run", "-Dspring-boot.run.arguments=--server.port=$BackendPort")
} elseif (Test-Path (Join-Path $backendPath "target\reparacion-0.0.1-SNAPSHOT.jar")) {
    $backendCommand = "java"
    $backendArgs = @("-jar", (Join-Path $backendPath "target\reparacion-0.0.1-SNAPSHOT.jar"), "--server.port=$BackendPort")
} else {
    Write-Host "[ERROR] No se encontro Maven ni JAR compilado." -ForegroundColor Red
    Write-Host "        Ejecuta primero: mvn clean install -DskipTests" -ForegroundColor Yellow
    exit 1
}

$backendProc = Start-Process -FilePath $backendCommand -ArgumentList $backendArgs -WorkingDirectory $backendPath -PassThru -NoNewWindow
Write-Host "  Backend PID: $($backendProc.Id)" -ForegroundColor Gray

# Esperar a que backend este listo
Write-Host "Esperando a que el Backend este listo... (hasta 30s)" -ForegroundColor Cyan
$backendReady = $false
for ($i = 0; $i -lt 30; $i++) {
    try {
    $health = Invoke-WebRequest -Uri "http://${IP}:$BackendPort/actuator/health" -TimeoutSec 2 -ErrorAction Stop
        if ($health.StatusCode -eq 200) { $backendReady = $true; break }
    } catch { }
    Start-Sleep -Seconds 1
    Write-Host "." -NoNewline
}
Write-Host ""
if ($backendReady) {
    Write-Host "[OK] Backend listo en http://${IP}:$BackendPort" -ForegroundColor Green
} else {
    Write-Host "[WARN] Backend no respondio a tiempo. Continuando para iniciar el Frontend por si tarda mas..." -ForegroundColor Yellow
}

# Iniciar Frontend en primer plano
Write-Host "Iniciando Frontend (Expo)..." -ForegroundColor Cyan
Write-Host "  Puerto: $FrontendPort" -ForegroundColor Gray
Push-Location $frontendPath
if (-not (Test-Path "node_modules")) {
    Write-Host "Instalando dependencias npm (puede tardar)..." -ForegroundColor Yellow
    npm install
    npx expo install react-native-maps expo-location @expo/vector-icons
}
$env:REACT_NATIVE_API_URL = "http://${IP}:$BackendPort/api"
Write-Host "  Env: REACT_NATIVE_API_URL = $env:REACT_NATIVE_API_URL" -ForegroundColor Gray
Write-Host "Arrancando Metro Bundler..." -ForegroundColor Gray
npm start
Pop-Location

Write-Host "" 
Write-Host "Para detener el Backend, puede cerrar esta ventana o terminar el proceso PID $($backendProc.Id)." -ForegroundColor Yellow

