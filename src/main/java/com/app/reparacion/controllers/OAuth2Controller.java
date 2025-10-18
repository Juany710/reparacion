package com.app.reparacion.controllers;

import com.app.reparacion.models.*;
import com.app.reparacion.models.enums.*;
import com.app.reparacion.repositories.UsuarioRepository;
import com.app.reparacion.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@CrossOrigin(origins = "*")
public class OAuth2Controller {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/callback")
    @Transactional
    public ResponseEntity<?> loginSocial(
            @RequestParam String email,
            @RequestParam(required = false) String name,
            @RequestParam String providerId,
            @RequestParam String provider,
            @RequestParam(defaultValue = "CLIENTE") String tipo // CLIENTE o TECNICO
    ) {
        AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());

        Usuario usuario = usuarioRepo.findByEmail(email).orElseGet(() -> {
            Usuario nuevo = "TECNICO".equalsIgnoreCase(tipo) ? new Tecnico() : new Cliente();
            nuevo.setEmail(email);
            nuevo.setNombre(name);
            nuevo.setUsername(email.split("@")[0]);
            nuevo.setRol("TECNICO".equalsIgnoreCase(tipo) ? Rol.TECNICO : Rol.CLIENTE);
            nuevo.setAuthProvider(authProvider);
            nuevo.setFechaRegistro(LocalDate.now());
            return usuarioRepo.save(nuevo);
        });

        usuario.setUltimoAcceso(LocalDate.now());
        usuarioRepo.save(usuario);

        String token = jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User.builder()
                        .username(usuario.getEmail())
                        .password("")
                        .roles(usuario.getRol().name())
                        .build()
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", usuario.getEmail(),
                "rol", usuario.getRol().name(),
                "tipo", usuario.getClass().getSimpleName(),
                "provider", usuario.getAuthProvider().name()
        ));
    }
}
