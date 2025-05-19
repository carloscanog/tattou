package com.tattou.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tattou.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCompradorId(Long compradorId);

    List<Pedido> findByVendedorId(Long vendedorId);

    Optional<Pedido> findByIdAndCompradorId(Long pedidoId, Long compradorId);

    Optional<Pedido> findByIdAndVendedorId(Long pedidoId, Long vendedorId);

}
