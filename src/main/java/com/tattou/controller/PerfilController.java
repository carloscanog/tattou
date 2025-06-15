package com.tattou.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tattou.model.Cliente;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@RestController
@RequestMapping("/profile")
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
                    "tiktok", tatuador.getTiktok(),
                    "fotoPerfil", usuario.getFotoPerfil()
                ), "usuarioId", usuario.getId()
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
                    "intereses", cliente.getIntereses(),
                    "fotoPerfil", usuario.getFotoPerfil()
                ), "usuarioId", usuario.getId()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene un rol asignado.");
        }
    }

    @PutMapping("/foto")
    public ResponseEntity<?> actualizarFotoPerfil(@RequestParam("archivo") MultipartFile archivo) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
            }

            String email = auth.getName();
            Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorEmail(email);
            // Si no existe el usuario, se lanza la debida respuesta
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Usuario usuario = usuarioOpt.get();

            // Se guarda el archivo con el id del usuario
            String nombreArchivo = "usuario_" + usuario.getId() + "_" + archivo.getOriginalFilename();
            Path rutaDestino = Paths.get("src/main/resources/static/uploads/usuarios/" + nombreArchivo);
            Files.createDirectories(rutaDestino.getParent());
            Files.write(rutaDestino, archivo.getBytes());

            // Se actualiza la ruta en el usuario
            usuario.setFotoPerfil("/uploads/usuarios/" + nombreArchivo);
            usuarioService.guardar(usuario);

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Foto actualizada correctamente");
            respuesta.put("fotoPerfil", usuario.getFotoPerfil());

            return ResponseEntity.ok(respuesta);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioActual(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id).get();

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
                    "tiktok", tatuador.getTiktok(),
                    "fotoPerfil", usuario.getFotoPerfil()
                ), "usuarioId", usuario.getId()
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
                    "intereses", cliente.getIntereses(),
                    "fotoPerfil", usuario.getFotoPerfil()
                ), "usuarioId", usuario.getId()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene un rol asignado.");
        }
    }

}
