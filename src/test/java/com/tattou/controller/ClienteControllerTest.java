package com.tattou.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.tattou.dto.ClienteRegistroRequest;
import com.tattou.model.Cliente;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    public ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TatuadorService tatuadorService;

    @Test
    public void obtenerClientesTest() {
        Cliente cliente = new Cliente();
        List<Cliente> clientes = new ArrayList<Cliente>();
        clientes.add(cliente);

        Mockito.when(clienteService.obtenerClientes()).thenReturn(clientes);

        List<Cliente> result = clienteController.obtenerClientes();
        Assert.notNull(result, "error");
    }

    @Test
    public void completarRegistroClienteTest() {
        ClienteRegistroRequest dto = new ClienteRegistroRequest();
        dto.setIntereses(List.of("hola", "adios"));
        dto.setCiudad("ciudad");
        dto.setUsuarioId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        Mockito.when(usuarioService.obtenerUsuarioPorId(dto.getUsuarioId())).thenReturn(usuarioOpt);

        Optional<Cliente> noCliente = Optional.empty();
        Optional<Tatuador> noTatuador = Optional.empty();

        Mockito.when(clienteService.obtenerClientePorUsuarioId(usuario.getId())).thenReturn(noCliente);
        Mockito.when(tatuadorService.obtenerTatuadorPorUsuarioId(usuario.getId())).thenReturn(noTatuador);

        Cliente cliente = new Cliente();
        Mockito.when(clienteService.guardar(Mockito.any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> result = clienteController.completarRegistroCliente(dto);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerClientesPorIdTest() {
        Cliente cliente = new Cliente();
        Optional<Cliente> clienteOpt = Optional.of(cliente);

        Mockito.when(clienteService.obtenerClientePorId(1L)).thenReturn(clienteOpt);

        Optional<Cliente> result = clienteController.obtenerClientePorId(1L);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidoPorIdYCompradorIdTest() {
        Pedido pedido = new Pedido();
        Optional<Pedido> pedidoOpt = Optional.of(pedido);

        Mockito.when(pedidoService.obtenerPedidoPorIdYCompradorId(1L, 1L)).thenReturn(pedidoOpt);

        Optional<Pedido> result = clienteController.obtenerPedidoPorIdYCompradorId(1L, 1L);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearCliente() {
        Cliente cliente = new Cliente();
        Cliente cliente2 = new Cliente();

        Mockito.when(clienteService.guardar(cliente)).thenReturn(cliente2);

        Cliente result = clienteController.crearCliente(cliente);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarClientePorId() {
        clienteController.eliminarClientePorId(1L);
        Assert.notNull(clienteController, "error");
    }

    @Test
    public void actualizarClienteTest() {
        Cliente datos = new Cliente();
        datos.setCiudad("ciudad");
        datos.setIntereses(List.of("hola", "adios"));

        Optional<Cliente> clienteOpt = Optional.of(datos);
        Mockito.when(clienteService.obtenerClientePorId(1L)).thenReturn(clienteOpt);

        ResponseEntity<Cliente> result = clienteController.actualizarCliente(1L, datos);
        Assert.notNull(result, "error");
    }

}
