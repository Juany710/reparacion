import axios from 'axios';
import * as SecureStore from 'expo-secure-store';
import { jwtDecode } from 'jwt-decode';

// Get API base URL from environment variable or use default
// For local dev, can set REACT_NATIVE_API_URL env var or auto-detect
const API_BASE_URL = process.env.REACT_NATIVE_API_URL || 'http://localhost:8080/api';

console.log(`[API] Connecting to backend: ${API_BASE_URL}`);

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

// Request interceptor - Agregar JWT token
api.interceptors.request.use(
  async (config) => {
    try {
      const token = await SecureStore.getItemAsync('authToken');
      if (token) {
        // Verificar si el token es válido
        const decoded = jwtDecode(token);
        const now = Date.now() / 1000;
        if (decoded.exp > now) {
          config.headers.Authorization = `Bearer ${token}`;
        } else {
          // Token expirado, eliminar
          await SecureStore.deleteItemAsync('authToken');
        }
      }
    } catch (error) {
      console.log('Error al obtener token:', error);
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor - Manejar 401
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      try {
        await SecureStore.deleteItemAsync('authToken');
      } catch (e) {
        console.log('Error al eliminar token:', e);
      }
    }
    return Promise.reject(error);
  }
);

// ========================================
// 🔐 ENDPOINTS DE AUTENTICACIÓN
// ========================================
export const authService = {
  // POST /api/auth/register - Registrar nuevo usuario
  register: (email, password, nombre, apellido, username, tipo) =>
    api.post('/auth/register', {
      email,
      password,
      nombre,
      apellido,
      username,
      tipo, // "CLIENTE" o "TECNICO"
    }),

  // POST /api/auth/login - Login
  login: (usernameOrEmail, password) =>
    api.post('/auth/login', {
      usernameOrEmail,
      password,
    }),

  // GET /api/auth/verify - Verificar token
  verifyToken: () =>
    api.get('/auth/verify'),
};

// ========================================
// 👤 ENDPOINTS DE USUARIO
// ========================================
// ===== USUARIOS: Clientes y Técnicos (corresponde a los controladores reales)
export const clienteService = {
  // GET /api/clientes/{id} - Obtener datos del cliente (completo)
  getCliente: (id) => api.get(`/clientes/${id}`),

  // PUT /api/clientes/{id} - Actualizar datos del cliente
  updateCliente: (id, data) => api.put(`/clientes/${id}`, data),

  // GET /api/clientes - Listar clientes (admin/soporte)
  listClientes: () => api.get('/clientes'),

  // DELETE /api/clientes/{id} - Eliminar cliente
  deleteCliente: (id) => api.delete(`/clientes/${id}`),
};

export const tecnicoService = {
  // GET /api/tecnicos/{id}
  getTecnico: (id) => api.get(`/tecnicos/${id}`),

  // PUT /api/tecnicos/{id}
  updateTecnico: (id, data) => api.put(`/tecnicos/${id}`, data),

  // GET /api/tecnicos - Listar técnicos
  listTecnicos: () => api.get('/tecnicos'),

  // GET /api/tecnicos/{id}/categorias
  getCategorias: (id) => api.get(`/tecnicos/${id}/categorias`),

  // PUT /api/tecnicos/{id}/categorias
  updateCategorias: (id, data) => api.put(`/tecnicos/${id}/categorias`, data),

  // GET /api/tecnicos/{id}/servicios
  getServiciosTecnico: (id) => api.get(`/tecnicos/${id}/servicios`),
};

// ========================================
// 💳 ENDPOINTS DE PAGO (STRIPE)
// ========================================
export const pagoService = {
  // POST /api/pagos/procesar - Procesar un pago
  procesarPago: (monto, idOferta, paymentMethodId, metodo = 'TARJETA', moneda = 'EUR') =>
    api.post('/pagos/procesar', {
      monto,
      idOferta,
      paymentMethodId,
      metodo,
      moneda,
    }),

  // GET /api/pagos/historial - Obtener historial de pagos del cliente
  obtenerHistorial: (idCliente) =>
    api.get(`/pagos/historial?idCliente=${idCliente}`),

  // GET /api/pagos/{idPago} - Obtener detalles de un pago
  obtenerPago: (idPago) =>
    api.get(`/pagos/${idPago}`),

  // GET /api/pagos/referencia/{referencia} - Obtener pago por referencia Stripe
  obtenerPagoPorReferencia: (referencia) =>
    api.get(`/pagos/referencia/${referencia}`),

  // POST /api/pagos/{idPago}/reembolsar - Reembolsar un pago
  reembolsar: (idPago) =>
    api.post(`/pagos/${idPago}/reembolsar`),

  // GET /api/pagos/estado/{estado} - Obtener pagos por estado
  obtenerPagosPorEstado: (estado) =>
    api.get(`/pagos/estado/${estado}`),
};

// ========================================
// � ENDPOINTS DE UBICACIONES (GOOGLE MAPS)
// ========================================
export const ubicacionService = {
  // POST /api/ubicaciones/actualizar - Actualizar ubicación de técnico (GPS)
  actualizarUbicacion: (idTecnico, latitud, longitud) =>
    api.post('/ubicaciones/actualizar', null, {
      params: { idTecnico, latitud, longitud },
    }),

  // GET /api/ubicaciones/tecnico/{idTecnico}/ultima - Obtener última ubicación
  obtenerUltimaUbicacion: (idTecnico) =>
    api.get(`/ubicaciones/tecnico/${idTecnico}/ultima`),

  // GET /api/ubicaciones/tecnico/{idTecnico}/historial - Obtener historial de ubicaciones
  obtenerHistorialUbicaciones: (idTecnico) =>
    api.get(`/ubicaciones/tecnico/${idTecnico}/historial`),

  // GET /api/ubicaciones/tecnico/{idTecnico}/rango - Obtener ubicaciones en rango de fechas
  obtenerUbicacionesPorRango: (idTecnico, inicio, fin) =>
    api.get(`/ubicaciones/tecnico/${idTecnico}/rango`, {
      params: { inicio, fin },
    }),

  // GET /api/ubicaciones/{idUbicacion} - Obtener detalles de ubicación
  obtenerUbicacion: (idUbicacion) =>
    api.get(`/ubicaciones/${idUbicacion}`),

  // GET /api/ubicaciones/distancia - Calcular distancia entre dos puntos (Haversine)
  calcularDistancia: (lat1, lng1, lat2, lng2) =>
    api.get('/ubicaciones/distancia', {
      params: { lat1, lng1, lat2, lng2 },
    }),
};

// ========================================
// �📋 ENDPOINTS DE SOLICITUDES Y SERVICIOS
// ========================================
export const solicitudService = {
  // GET /api/solicitudes - Listar solicitudes del usuario
  getSolicitudes: () =>
    api.get('/solicitudes'),

  // POST /api/solicitudes - Crear nueva solicitud
  createSolicitud: (data) =>
    api.post('/solicitudes', data),

  // GET /api/solicitudes/{id} - Obtener detalles de solicitud
  getSolicitud: (id) =>
    api.get(`/solicitudes/${id}`),

  // PUT /api/solicitudes/{id} - Actualizar solicitud
  // Para cambiar estado el backend expone: PUT /api/solicitudes/{id}/estado?estado=ESTADO
  cambiarEstado: (id, estado) =>
    api.put(`/solicitudes/${id}/estado?estado=${encodeURIComponent(estado)}`),

  // DELETE /api/solicitudes/{id} - Cancelar solicitud
  cancelSolicitud: (id) =>
    api.delete(`/solicitudes/${id}`),
};

export const servicioService = {
  // GET /api/servicios - Listar servicios disponibles
  getServicios: () =>
    api.get('/servicios'),

  // GET /api/servicios/{id} - Obtener detalles de servicio
  getServicio: (id) =>
    api.get(`/servicios/${id}`),
};

// ========================================
// 💬 ENDPOINTS DE CHAT
// ========================================
export const chatService = {
  // El backend expone /api/chat con diferentes rutas y un objeto Chat en el body
  enviarMensaje: (chatObj) => api.post('/chat', chatObj),
  marcarComoEnviado: (id) => api.put(`/chat/${id}/enviado`),
  marcarComoRecibido: (id) => api.put(`/chat/${id}/recibido`),
  marcarComoVisto: (id) => api.put(`/chat/${id}/visto`),
  // Listar mensajes por servicio o por ticket de soporte
  getMensajesPorServicio: (idServicio) => api.get(`/chat/servicio/${idServicio}`),
  getMensajesPorTicket: (idTicket) => api.get(`/chat/ticket/${idTicket}`),
  // Obtener resumen (DTO) de mensajes por servicio
  getResumenPorServicio: (idServicio) => api.get(`/chat/servicio/${idServicio}/resumen`),
};

// ========================================
// 🔔 ENDPOINTS DE NOTIFICACIONES (FIREBASE FCM)
// ========================================
export const notificacionService = {
  // POST /api/notificaciones/registrar-dispositivo - Registrar token de dispositivo
  registrarDispositivo: (idUsuario, token, plataforma) =>
    api.post('/notificaciones/registrar-dispositivo', null, {
      params: { idUsuario, token, plataforma },
    }),

  // POST /api/notificaciones/desregistrar-dispositivo - Desregistrar dispositivo
  desregistrarDispositivo: (token) =>
    api.post('/notificaciones/desregistrar-dispositivo', null, {
      params: { token },
    }),

  // POST /api/notificaciones/enviar - Enviar notificación individual
  enviarNotificacion: (token, titulo, cuerpo, icono = null, sonido = null) =>
    api.post('/notificaciones/enviar', null, {
      params: { token, titulo, cuerpo, icono, sonido },
    }),

  // POST /api/notificaciones/enviar-multicast - Enviar notificaciones a múltiples usuarios
  enviarNotificacionMulticast: (tokens, titulo, cuerpo, icono = null, sonido = null) =>
    api.post('/notificaciones/enviar-multicast', tokens, {
      params: { titulo, cuerpo, icono, sonido },
    }),

  // POST /api/notificaciones/tecnico/{idTecnico} - Enviar notificación a técnico
  enviarNotificacionATecnico: (idTecnico, titulo, cuerpo) =>
    api.post(`/notificaciones/tecnico/${idTecnico}`, null, {
      params: { titulo, cuerpo },
    }),

  // POST /api/notificaciones/tema/{tema} - Enviar notificación a tema (broadcast)
  enviarNotificacionTema: (tema, titulo, cuerpo) =>
    api.post(`/notificaciones/tema/${tema}`, null, {
      params: { titulo, cuerpo },
    }),

  // POST /api/notificaciones/suscribir-tema - Suscribirse a un tema
  suscribirATema: (token, tema) =>
    api.post('/notificaciones/suscribir-tema', null, {
      params: { token, tema },
    }),

  // POST /api/notificaciones/desuscribir-tema - Desuscribirse de un tema
  desuscribirDeTema: (token, tema) =>
    api.post('/notificaciones/desuscribir-tema', null, {
      params: { token, tema },
    }),
};

// ========================================
// 💼 ENDPOINTS DE OFERTAS (TÉCNICO)
// ========================================
export const ofertaService = {
  // GET /api/ofertas - Listar ofertas creadas por técnico
  getOfertas: () =>
    api.get('/ofertas'),

  // POST /api/ofertas - Crear oferta para una solicitud
  createOferta: (data) =>
    api.post('/ofertas', data),

  // PUT /api/ofertas/{id} - Actualizar oferta
  updateOferta: (id, data) =>
    api.put(`/ofertas/${id}`, data),

  // DELETE /api/ofertas/{id} - Cancelar oferta
  deleteOferta: (id) =>
    api.delete(`/ofertas/${id}`),
};

// ========================================
// ⭐ ENDPOINTS DE CALIFICACIONES
// ========================================
export const calificacionService = {
  // POST /api/calificaciones - Crear calificación
  createCalificacion: (data) =>
    api.post('/calificaciones', data),

  // GET /api/calificaciones/usuario/{id} - Obtener calificaciones de usuario
  getCalificacionesUsuario: (id) =>
    api.get(`/calificaciones/usuario/${id}`),
};

export default api;

