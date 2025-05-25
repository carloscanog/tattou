package com.tattou.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tattou.dto.ClienteRegistroRequest;
import com.tattou.model.Cliente;
import com.tattou.model.Pedido;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    private final PedidoService pedidoService;

    private final UsuarioService usuarioService;

    private final TatuadorService tatuadorService;

    public ClienteController(ClienteService clienteService, PedidoService pedidoService,
        UsuarioService usuarioService, TatuadorService tatuadorService) {
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.tatuadorService = tatuadorService;
    }

    @GetMapping
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @PostMapping("/registro")
    public ResponseEntity<Cliente> completarRegistroCliente(@Valid @RequestBody ClienteRegistroRequest dto) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(dto.getUsuarioId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "Usuario no encontrado en base de datos"));
        
        if (clienteService.obtenerClientePorUsuarioId(usuario.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya está registrado como cliente"); 
        } else if (tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya está registrado como tatuador"); 
        }
        
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        // Se sustituyen las posibles comas por no caracteres
        String interesesDto = dto.getIntereses().trim().replace(",", " ")
            .replace(".", " ").replace("  ", " ").replace("  ", " ");
        // Se separa la cadena recogida por espacios, y el array generado se convierte en una lista
        List<String> intereses = new ArrayList<String>(Arrays.asList(interesesDto.split(" ")));
        cliente.setIntereses(intereses);
        cliente.setCiudad(dto.getCiudad());

        Cliente guardado = clienteService.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/{id}/pedidos")
    public List<Pedido> obtenerPedidosPorCompradorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidosPorCompradorId(id);
    }

    @GetMapping("/{id}")
    public Optional<Cliente> obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @GetMapping("/{compradorId}/pedidos/{pedidoId}")
    public Optional<Pedido> obtenerPedidoPorIdYCompradorId(@PathVariable Long compradorId, @PathVariable Long pedidoId) {
        return pedidoService.obtenerPedidoPorIdYCompradorId(pedidoId, compradorId);
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminarClientePorId(@PathVariable Long id) {
        clienteService.eliminarClientePorId(id);
    }

}
