package com.tattou.controller;

import java.util.List;
import java.util.Map;
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
import com.tattou.dto.UsuarioRegistroResponse;
import com.tattou.model.Usuario;
import com.tattou.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
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
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest dto) {
        // Lo primero es comprobar que no haya ningun usuario con el mismo email registrado
        if (usuarioService.obtenerUsuarioPorEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", "Este email ya está registrado"));
        }
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioRegistroResponse(guardado.getId(), guardado.getEmail()));
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
    }

}
