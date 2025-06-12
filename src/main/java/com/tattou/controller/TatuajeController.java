package com.tattou.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tattou.model.Tatuador;
import com.tattou.model.Tatuaje;
import com.tattou.model.Usuario;
import com.tattou.service.TatuadorService;
import com.tattou.service.TatuajeService;
import com.tattou.service.UsuarioService;

@RestController
@RequestMapping("/tatuajes")
@CrossOrigin(origins = "*")
public class TatuajeController  {

    private final TatuajeService tatuajeService;

    private final UsuarioService usuarioService;

    private final TatuadorService tatuadorService;

    public TatuajeController(TatuajeService tatuajeService, UsuarioService usuarioService, TatuadorService tatuadorService) {
        this.tatuajeService = tatuajeService;
        this.usuarioService = usuarioService;
        this.tatuadorService = tatuadorService;
    }

    @GetMapping
    public List<Tatuaje> obtenerTatuajes() {
        return tatuajeService.obtenerTatuajes();
    }

    @GetMapping("/{id}")
    public Optional<Tatuaje> obtenerTatuajePorId(@PathVariable Long id) {
        return tatuajeService.obtenerTatuajePorId(id);
    }

    @GetMapping("/autor/{autorId}")
    public List<Tatuaje> obtenerTatuajesPorAutorId(@PathVariable Long autorId) {
        return tatuajeService.obtenerPorAutorId(autorId);
    }

    @DeleteMapping("/{id}")
    public void eliminarTatuajePorId(@PathVariable Long id) {
        tatuajeService.eliminarTatuajePorId(id);
    }

    @PostMapping
    public ResponseEntity<?> crearTatuaje(
            @RequestParam("titulo") String titulo,
            @RequestParam("etiquetas") String etiquetasStr,
            @RequestParam("imagen") MultipartFile imagen,
            Authentication authentication) {

        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email).orElseThrow();
        Tatuador tatuador = tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId()).orElseThrow();

        // Se convierten las etiquetas a una lista de Strings
        List<String> etiquetas = Arrays.stream(etiquetasStr.split(","))
                                    .map(String::trim)
                                    .filter(e -> !e.isEmpty())
                                    .collect(Collectors.toList());

        String nombreArchivo = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
        Path rutaImagen = Paths.get("src/main/resources/static/uploads", nombreArchivo);

        try {
            Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }

        Tatuaje tatuaje = new Tatuaje();
        tatuaje.setTitulo(titulo);
        tatuaje.setEtiquetas(etiquetas);
        tatuaje.setImagen("/uploads/tatuajes" + nombreArchivo);
        tatuaje.setAutor(tatuador);

        Tatuaje guardado = tatuajeService.guardarTatuaje(tatuaje);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/mios")
    public ResponseEntity<?> obtenerTatuajesDelTatuadorAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado.");
        }

        String email = auth.getName();
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (usuarioService.esRegistradoComoCliente(usuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado.");
        }

        Optional<Tatuador> tatuadorOpt = tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId());

        if (tatuadorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tatuador no encontrado.");
        }

        List<Tatuaje> tatuajes = tatuajeService.obtenerPorAutorId(tatuadorOpt.get().getId());
        return ResponseEntity.ok(tatuajes);
    }
    
}
