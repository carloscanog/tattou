package com.tattou.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tattou.dto.TatuadorRegistroRequest;
import com.tattou.model.Disenyo;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Tatuaje;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.DisenyoService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.TatuajeService;
import com.tattou.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tatuadores")
@CrossOrigin(origins = "http://localhost:4200")
public class TatuadorController {

    private final TatuadorService tatuadorService;

    private final TatuajeService tatuajeService;

    private final DisenyoService disenyoService;
    
    private final PedidoService pedidoService;

    private final UsuarioService usuarioService;

    private final ClienteService clienteService;

    public TatuadorController(TatuadorService tatuadorService, TatuajeService tatuajeService,
        DisenyoService disenyoService, PedidoService pedidoService, UsuarioService usuarioService,
        ClienteService clienteService) {
        this.tatuadorService = tatuadorService;
        this.tatuajeService = tatuajeService;
        this.disenyoService = disenyoService;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Tatuador> obtenerTodos() {
        return tatuadorService.obtenerTatuadores();
    }

    @PostMapping("/registro")
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    public ResponseEntity<Tatuador> completarRegistroTatuador(@Valid @RequestBody TatuadorRegistroRequest dto) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(dto.getUsuarioId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "Usuario no encontrado en base de datos"));
        
        if (tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya está registrado como tatuador");
        } else if (clienteService.obtenerClientePorUsuarioId(usuario.getId()).isPresent()) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya está registrado como cliente"); 
        }

        Tatuador tatuador = new Tatuador();
        tatuador.setUsuario(usuario);
        tatuador.setBiografia(dto.getBiografia());
        tatuador.setEstilos(dto.getEstilos());
        tatuador.setInstagram(dto.getInstagram());
        tatuador.setTiktok(dto.getTiktok());
        tatuador.setUbicacion(dto.getUbicacion());

        Tatuador guardado = tatuadorService.guardar(tatuador);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/{id}/tatuajes")
    public List<Tatuaje> obtenerTatuajesPorAutorId(@PathVariable Long id) {
        return tatuajeService.obtenerPorAutorId(id);
    }

    @GetMapping("/{id}/disenyos")
    public List<Disenyo> obtenerDisenyosPorAutorId(@PathVariable Long id) {
        return disenyoService.obtenerDisenyosPorAutorId(id);
    }

    @GetMapping("/{id}/pedidos")
    public List<Pedido> obtenerPedidosPorVendedorId(@PathVariable Long vendedorId) {
        return pedidoService.obtenerPedidosPorVendedorId(vendedorId);
    }

    @GetMapping("/{id}")
    public Optional<Tatuador> obtenerPorId(@PathVariable Long id) {
        return tatuadorService.obtenerTatuadorPorId(id);
    }

    @GetMapping("/{vendedorId}/pedidos/{pedidoId}")
    public Optional<Pedido> obtenerPedidoPorIdYVendedorId(@PathVariable Long vendedorId, @PathVariable Long pedidoId) {
        return pedidoService.obtenerPedidoPorIdYVendedorId(pedidoId, vendedorId);
    }

    @PostMapping
    public Tatuador crear(@RequestBody Tatuador tatuador) {
        return tatuadorService.guardar(tatuador);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tatuadorService.eliminarTatuadorPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tatuador> actualizarTatuador(@PathVariable Long id, @RequestBody Tatuador datos) {
        Tatuador tatuador = tatuadorService.obtenerTatuadorPorId(id).get();
        tatuador.setBiografia(datos.getBiografia());
        tatuador.setUbicacion(datos.getUbicacion());
        tatuador.setInstagram(datos.getInstagram());
        tatuador.setTiktok(datos.getTiktok());
        tatuador.setEstilos(datos.getEstilos());
        return ResponseEntity.ok(tatuadorService.guardar(tatuador));
    }


}
