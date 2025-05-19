package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Pedido;

public interface PedidoService {

    Optional<Pedido> obtenerPedidoPorId(Long id);

    List<Pedido> obtenerPedidosPorCompradorId(Long compradorId);

    List<Pedido> obtenerPedidosPorVendedorId(Long vendedorId);

    Optional<Pedido> obtenerPedidoPorIdYCompradorId(Long pedidoId, Long compradorId);

    Optional<Pedido> obtenerPedidoPorIdYVendedorId(Long pedidoId, Long vendedorId);

    Pedido crearPedido(Pedido pedido);

}
