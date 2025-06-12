package com.tattou.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tattou.model.Cliente;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PerfilController {

    private final UsuarioService usuarioService;

    private final TatuadorService tatuadorService;

    private final ClienteService clienteService;

    public PerfilController(UsuarioService usuarioService, TatuadorService tatuadorService,
        ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.tatuadorService = tatuadorService;
        this.clienteService = clienteService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email).get();

        if (usuarioService.esRegistradoComoTatuador(usuario)) {
            Tatuador tatuador = tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId()).get();
            return ResponseEntity.ok(Map.of(
                "rol", "TATUADOR",
                "perfil", Map.of(
                    "id", tatuador.getId(),
                    "email", usuario.getEmail(),
                    "nombre", usuario.getNombre(),
                    "apellidos", usuario.getApellidos(),
                    "biografia", tatuador.getBiografia(),
                    "ubicacion", tatuador.getUbicacion(),
                    "estilos", tatuador.getEstilos(),
                    "instagram", tatuador.getInstagram(),
                    "tiktok", tatuador.getTiktok()
                )
            )); 
        } else if (usuarioService.esRegistradoComoCliente(usuario)) {
            Cliente cliente = clienteService.obtenerClientePorUsuarioId(usuario.getId()).get();
            return ResponseEntity.ok(Map.of(
                "rol", "CLIENTE",
                "perfil", Map.of(
                    "id", cliente.getId(),
                    "email", usuario.getEmail(),
                    "nombre", usuario.getNombre(),
                    "apellidos", usuario.getApellidos(),
                    "ciudad", cliente.getCiudad(),
                    "intereses", cliente.getIntereses()
                )
            ));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene un rol asignado.");
        }
    }
}
