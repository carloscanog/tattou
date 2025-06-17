package com.tattou.dto;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class ClienteRegistroRequestTest {

    @InjectMocks
    private ClienteRegistroRequest clienteRegistroRequest;

    @Test
    public void setUsuarioIdTest() {
        Long usuarioId = 1L;
        clienteRegistroRequest.setUsuarioId(usuarioId);
        Assert.isTrue(usuarioId.equals(clienteRegistroRequest.getUsuarioId()), "error");
    }

    @Test
    public void setInteresesTest() {
        List<String> intereses = List.of("hola", "adios");
        clienteRegistroRequest.setIntereses(intereses);
        Assert.isTrue(intereses.equals(clienteRegistroRequest.getIntereses()), "error");
    }

    @Test
    public void setCiudadTest() {
        String ciudad = "ciudad";
        clienteRegistroRequest.setCiudad(ciudad);
        Assert.isTrue(ciudad.equals(clienteRegistroRequest.getCiudad()), "error");
    }

}
