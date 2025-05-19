package com.tattou.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tattou.model.Pedido;
import com.tattou.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
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

}
