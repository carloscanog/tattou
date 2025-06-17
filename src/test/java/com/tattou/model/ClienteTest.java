package com.tattou.model;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class ClienteTest {

    @InjectMocks
    private Cliente cliente;

    @Test
    public void setIdTest() {
        Long id = 1L;
        cliente.setId(id);
        Assert.isTrue(id.equals(cliente.getId()), "error");
    }

    @Test
    public void setUsuarioTest() {
        Usuario usuario = new Usuario();
        cliente.setUsuario(usuario);
        Assert.isTrue(usuario.equals(cliente.getUsuario()), "error");
    }

    @Test
    public void setCiudadTest() {
        String ciudad = "ciudad";
        cliente.setCiudad(ciudad);
        Assert.isTrue(ciudad.equals(cliente.getCiudad()), "error");
    }

    @Test
    public void setInteresesTest() {
        List<String> intereses = List.of("hola", "adios");
        cliente.setIntereses(intereses);
        Assert.isTrue(intereses.equals(cliente.getIntereses()), "error");
    }

}
