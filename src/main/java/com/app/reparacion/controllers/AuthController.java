package com.app.reparacion.controllers;

import com.app.reparacion.models.*;
import com.app.reparacion.models.enums.*;
import com.app.reparacion.repositories.UsuarioRepository;
import com.app.reparacion.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    // 🔹 Registro: permite elegir tipo de usuario (cliente o técnico)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String username = request.get("username");
        String password = request.get("password");
        String nombre = request.get("nombre");
        String apellido = request.get("apellido");
        String tipo = request.get("tipo"); // "CLIENTE" o "TECNICO"

        if (usuarioRepo.existsByEmail(email) || usuarioRepo.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario o email ya registrados"));
        }

        Usuario nuevo;
        if ("TECNICO".equalsIgnoreCase(tipo)) {
            nuevo = new Tecnico();
            nuevo.setRol(Rol.TECNICO);
        } else {
            nuevo = new Cliente();
            nuevo.setRol(Rol.CLIENTE);
        }

        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setEmail(email);
        nuevo.setUsername(username);
        nuevo.setPassword(passwordEncoder.encode(password));
        nuevo.setFechaRegistro(LocalDate.now());
        nuevo.setAuthProvider(AuthProvider.LOCAL);

        usuarioRepo.save(nuevo);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente como " + tipo));
    }

    // 🔹 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String usernameOrEmail = request.get("usernameOrEmail");
        String password = request.get("password");

        authManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(usernameOrEmail);
        final String jwt = jwtUtil.generateToken(userDetails);

        Usuario usuario = usuarioRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail).orElseThrow();
        usuario.setUltimoAcceso(LocalDate.now());
        usuarioRepo.save(usuario);

        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "rol", usuario.getRol().name(),
                "nombre", usuario.getNombre(),
                "tipo", usuario.getClass().getSimpleName()
        ));
    }

    @GetMapping("/verify")
public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    Usuario usuario = usuarioRepo.findByEmailOrUsername(username, username).orElse(null);
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
    }

    boolean valid = jwtUtil.validateToken(token, 
        org.springframework.security.core.userdetails.User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .roles(usuario.getRol().name())
            .build()
    );

    return ResponseEntity.ok(Map.of(
            "valid", valid,
            "usuario", usuario.getEmail(),
            "rol", usuario.getRol().name(),
            "tipo", usuario.getClass().getSimpleName()
    ));
}
}