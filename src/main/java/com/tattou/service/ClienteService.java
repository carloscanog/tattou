package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Cliente;

public interface ClienteService {

    List<Cliente> obtenerClientes();

    Optional<Cliente> obtenerClientePorId(Long id);

    Optional<Cliente> obtenerClientePorUsuarioId(Long usuarioId);

    Cliente crearCliente(Cliente cliente);
    
    void eliminarClientePorId(Long id);

}
