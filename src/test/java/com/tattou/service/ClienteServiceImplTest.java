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
import com.tattou.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteServiceImpl;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    public void obtenerClientesTest() {
        List<Cliente> clientes = List.of(new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);
        List<Cliente> result = clienteServiceImpl.obtenerClientes();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerClientePorIdTest() {
        Long id = 1L;
        Optional<Cliente> clienteOpt = Optional.of(new Cliente());
        when(clienteRepository.findById(id)).thenReturn(clienteOpt);
        Optional<Cliente> result = clienteServiceImpl.obtenerClientePorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerClientePorUsuarioIdTest() {
        Long usuarioId = 1L;
        Optional<Cliente> clienteOpt = Optional.of(new Cliente());
        when(clienteRepository.findByUsuarioId(usuarioId)).thenReturn(clienteOpt);
        Optional<Cliente> result = clienteServiceImpl.obtenerClientePorUsuarioId(usuarioId);
        Assert.notNull(result, "error");
    }

    @Test
    public void guardarTest() {
        Cliente cliente = new Cliente();
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        Cliente result = clienteServiceImpl.guardar(cliente);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarClientePorIdTest() {
        Long id = 1L;
        clienteServiceImpl.eliminarClientePorId(id);
        Assert.notNull(clienteServiceImpl, "error");
    }

}
