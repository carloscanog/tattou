package com.tattou.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.tattou.model.Cliente;
import com.tattou.model.Disenyo;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.repository.ClienteRepository;
import com.tattou.repository.DisenyoRepository;
import com.tattou.repository.PedidoRepository;
import com.tattou.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl pedidoServiceImpl;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DisenyoRepository disenyoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    public void obtenerPedidosPorCompradorIdTest() {
        Long compradorId = 1L;
        List<Pedido> pedidos = List.of(new Pedido());
        when(pedidoRepository.findByCompradorId(compradorId)).thenReturn(pedidos);
        List<Pedido> result = pedidoServiceImpl.obtenerPedidosPorCompradorId(compradorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidosPorVendedorIdTest() {
        Long vendedorId = 1L;
        List<Pedido> pedidos = List.of(new Pedido());
        when(pedidoRepository.findByVendedorId(vendedorId)).thenReturn(pedidos);
        List<Pedido> result = pedidoServiceImpl.obtenerPedidosPorVendedorId(vendedorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidoPorIdTest() {
        Long id = 1L;
        Optional<Pedido> pedidoOpt = Optional.of(new Pedido());
        when(pedidoRepository.findById(id)).thenReturn(pedidoOpt);
        Optional<Pedido> result = pedidoServiceImpl.obtenerPedidoPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidoPorIdYCompradorIdTest() {
        Long pedidoId = 1L;
        Long compradorId = 1L;
        Optional<Pedido> clienteOpt = Optional.of(new Pedido());
        when(pedidoRepository.findByIdAndCompradorId(pedidoId, compradorId)).thenReturn(clienteOpt);
        Optional<Pedido> result = pedidoServiceImpl.obtenerPedidoPorIdYCompradorId(pedidoId, compradorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidoPorIdYVendedorIdTest() {
        Long pedidoId = 1L;
        Long vendedorId = 1L;
        Optional<Pedido> clienteOpt = Optional.of(new Pedido());
        when(pedidoRepository.findByIdAndVendedorId(pedidoId, vendedorId)).thenReturn(clienteOpt);
        Optional<Pedido> result = pedidoServiceImpl.obtenerPedidoPorIdYVendedorId(pedidoId, vendedorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearPedidoTest() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedido);
        Pedido result = pedidoServiceImpl.crearPedido(pedido);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearPedidoTrasPagoExitosoTest() {
        Long disenyoId = 1L;
        String clienteEmail = "email";
        Disenyo disenyo = new Disenyo();
        Tatuador tatuador = new Tatuador();
        disenyo.setAutor(tatuador);
        Optional<Disenyo> disenyoOptional = Optional.of(disenyo);
        when(disenyoRepository.findById(disenyoId)).thenReturn(disenyoOptional);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Optional<Usuario> usuarioOptional = Optional.of(usuario);
        when(usuarioRepository.findByEmail(clienteEmail)).thenReturn(usuarioOptional);

        Cliente cliente = new Cliente();
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findByUsuarioId(1L)).thenReturn(clienteOptional);

        pedidoServiceImpl.crearPedidoTrasPagoExitoso(disenyoId, clienteEmail);
        Assert.notNull(pedidoServiceImpl, "error");
    }

}
