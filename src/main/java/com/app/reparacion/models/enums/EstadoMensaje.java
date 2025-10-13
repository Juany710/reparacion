package com.app.reparacion.models.enums;

public enum EstadoMensaje {
    PENDIENTE,   // aún no se ha procesado o enviado
    ENVIADO,     // el emisor lo envió correctamente
    RECIBIDO,    // el receptor lo recibió (por ejemplo, lo cargó en pantalla)
    VISTO        // el receptor lo leyó

}
