package com.tattou.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.tattou.model.Cliente;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TatuadorService tatuadorService;

    @Mock
    private ClienteService clienteService;

    @Test
    public void obtenerPedidoPorIdTest() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        Optional<Pedido> pedidoOpt = Optional.of(pedido);

        Mockito.when(pedidoService.obtenerPedidoPorId(id)).thenReturn(pedidoOpt);

        Optional<Pedido> result = pedidoController.obtenerPedidoPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearPedidoTest() {
        Pedido pedido = new Pedido();

        when(pedidoService.crearPedido(pedido)).thenReturn(pedido);

        Pedido result = pedidoController.crearPedido(pedido);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidosDelTatuadorAutenticadoTest() {
        String email = "email@email.com";
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn(email);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorEmail(email)).thenReturn(usuarioOptional);
        when(usuarioService.esRegistradoComoCliente(usuario)).thenReturn(false);
        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(true);

        Tatuador tatuador = new Tatuador();
        tatuador.setId(1L);
        Optional<Tatuador> tatuadorOptional = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOptional);

        Pedido pedido = new Pedido();
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.obtenerPedidosPorVendedorId(1L)).thenReturn(pedidos);

        ResponseEntity<?> result = pedidoController.obtenerPedidosDelTatuadorAutenticado();
        Assert.notNull(result, "error");

        when(usuarioService.esRegistradoComoCliente(usuario)).thenReturn(true);
        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(false);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteService.obtenerClientePorUsuarioId(1L)).thenReturn(clienteOptional);
        when(pedidoService.obtenerPedidosPorCompradorId(1L)).thenReturn(pedidos);

        ResponseEntity<?> result2 = pedidoController.obtenerPedidosDelTatuadorAutenticado();
        Assert.notNull(result2, "error");
    }

}
