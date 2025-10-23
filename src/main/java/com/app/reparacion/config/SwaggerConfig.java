package com.app.reparacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 🔒 Configuración global de JWT para probar con "Authorize"
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)
                                        .name("Authorization")
                        ))
                // 📘 Información general del proyecto
                .info(new Info()
                        .title("API - Sistema de Reparaciones")
                        .version("1.0.0")
                        .description("""
                                Esta API gestiona todo el sistema de reparaciones: autenticación,
                                solicitudes, ofertas, servicios, chat y calificaciones.
                                Incluye soporte para JWT y OAuth2.
                                """)
                        .license(new License().name("Juan Ignacio Cabañas Hilbert - Proyecto Ingeniería en Software 2025"))
                );
        }
}