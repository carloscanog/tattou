package com.tattou.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tattou.model.Cliente;
import com.tattou.model.Disenyo;
import com.tattou.model.Pedido;
import com.tattou.model.Usuario;
import com.tattou.repository.ClienteRepository;
import com.tattou.repository.DisenyoRepository;
import com.tattou.repository.PedidoRepository;
import com.tattou.repository.UsuarioRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    private DisenyoRepository disenyoRepository;

    private UsuarioRepository usuarioRepository;

    private ClienteRepository clienteRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, DisenyoRepository disenyoRepository,
        ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.disenyoRepository = disenyoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Pedido> obtenerPedidosPorCompradorId(Long compradorId) {
        return pedidoRepository.findByCompradorId(compradorId);
    }

    @Override
    public List<Pedido> obtenerPedidosPorVendedorId(Long vendedorId) {
        return pedidoRepository.findByVendedorId(vendedorId);
    }

    @Override
    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> obtenerPedidoPorIdYCompradorId(Long pedidoId, Long compradorId) {
        return pedidoRepository.findByIdAndCompradorId(pedidoId, compradorId);
    }

    @Override
    public Optional<Pedido> obtenerPedidoPorIdYVendedorId(Long pedidoId, Long vendedorId) {
        return pedidoRepository.findByIdAndVendedorId(pedidoId, vendedorId);
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void crearPedidoTrasPagoExitoso(Long disenyoId, String clienteEmail) {
        // Busca el disenyo por el id
        Disenyo disenyo = disenyoRepository.findById(disenyoId)
            .orElseThrow(() -> new RuntimeException("Diseño no encontrado"));

        // Busca el usuario por el email
        Usuario usuario = usuarioRepository.findByEmail(clienteEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Busca el cliente por el id de usuario
        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crea el pedido con la fecha actual
        Pedido pedido = new Pedido();
        pedido.setDisenyo(disenyo);
        pedido.setComprador(cliente);
        pedido.setVendedor(disenyo.getAutor());
        pedido.setFecha(LocalDateTime.now());

        pedidoRepository.save(pedido);
    }

}
