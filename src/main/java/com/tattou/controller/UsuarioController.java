package com.tattou.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tattou.dto.UsuarioRegistroRequest;
import com.tattou.model.Usuario;
import com.tattou.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerUsuarios();
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña no puede estar vacía");
        } else {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Usuario guardado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    
    @GetMapping("/{id}:\\d+")
    public Optional<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
    }

    // @GetMapping("/me")
    // public ResponseEntity<?> getUsuarioActual() {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    //     if (auth == null) {
    //         return ResponseEntity.status(401).body("No hay autenticación");
    //     }

    //     if (!auth.isAuthenticated()) {
    //         return ResponseEntity.status(403).body("No estás autenticado");
    //     }

    //     System.out.println("🧠 Principal: " + auth.getPrincipal());
    //     System.out.println("👤 Nombre: " + auth.getName());
    //     System.out.println("🔑 Authorities: " + auth.getAuthorities());

    //     return ResponseEntity.ok("Autenticado como: " + auth.getName());
    // }

    // @GetMapping("/me")
    // public ResponseEntity<?> testMe() {
    //     System.out.println("💬 SE EJECUTA EL MÉTODO /me");
    //     return ResponseEntity.ok("El método /me se ejecutó correctamente.");
    // }


}
