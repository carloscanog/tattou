package com.tattou.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tattou.model.Cliente;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@RequestMapping("/pedidos")
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PedidoController {

    private PedidoService pedidoService;

    private UsuarioService usuarioService;

    private TatuadorService tatuadorService;

    private ClienteService clienteService;

    public PedidoController(PedidoService pedidoService, UsuarioService usuarioService, TatuadorService tatuadorService,
        ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.tatuadorService = tatuadorService;
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public Optional<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    @PostMapping()
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.crearPedido(pedido);
    }

    @GetMapping("/mios")
    public ResponseEntity<?> obtenerPedidosDelTatuadorAutenticado() {
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
        Boolean esCliente = usuarioService.esRegistradoComoCliente(usuario);
        Boolean esTatuador = usuarioService.esRegistradoComoTatuador(usuario);
        List<Pedido> pedidos = new ArrayList<Pedido>();

        if (esTatuador) {
            Optional<Tatuador> tatuadorOpt = tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId());
            if (tatuadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tatuador no encontrado.");
            }
            pedidos.addAll(pedidoService.obtenerPedidosPorVendedorId(tatuadorOpt.get().getId()));
        } else if (esCliente) {
            Optional<Cliente> clienteOpt = clienteService.obtenerClientePorUsuarioId(usuario.getId());
            if (clienteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
            }
            pedidos.addAll(pedidoService.obtenerPedidosPorCompradorId(clienteOpt.get().getId()));
        }

        return ResponseEntity.ok(pedidos);
    }

}
