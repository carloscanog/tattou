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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tattou.model.Disenyo;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.DisenyoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@RestController
@RequestMapping("/disenyos")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DisenyoController {

    private DisenyoService disenyoService;

    private UsuarioService usuarioService;

    private TatuadorService tatuadorService;

    public DisenyoController(DisenyoService disenyoService, UsuarioService usuarioService, TatuadorService tatuadorService) {
        this.disenyoService = disenyoService;
        this.usuarioService = usuarioService;
        this.tatuadorService = tatuadorService;
    }

    @GetMapping
    public List<Disenyo> obtenerDisenyos() {
        return disenyoService.obtenerDisenyos();
    }

    @GetMapping("/{id}")
    public Optional<Disenyo> obtenerDisenyoPorId(@PathVariable Long id) {
        return disenyoService.obtenerDisenyoPorId(id);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> crearDisenyo(
            @RequestParam("titulo") String titulo,
            @RequestParam("etiquetas") String etiquetasStr,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam("precio") Float precio,
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
        Path rutaImagen = Paths.get("src/main/resources/static/uploads/disenyos", nombreArchivo);

        try {
            Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }

        Disenyo disenyo = new Disenyo();
        disenyo.setTitulo(titulo);
        disenyo.setEtiquetas(etiquetas);
        disenyo.setPrecio(Float.valueOf(precio));
        disenyo.setImagen("/uploads/disenyos/" + nombreArchivo);
        disenyo.setAutor(tatuador);

        Disenyo guardado = disenyoService.guardarDisenyo(disenyo);
        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminarDisenyoPorId(@PathVariable Long id) {
        disenyoService.eliminarDisenyoPorId(id);
    }

    @GetMapping("/mios")
    public ResponseEntity<?> obtenerDisenyossDelTatuadorAutenticado() {
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

        List<Disenyo> disenyos = disenyoService.obtenerDisenyosPorAutorId(tatuadorOpt.get().getId());
        return ResponseEntity.ok(disenyos);
    }

}
