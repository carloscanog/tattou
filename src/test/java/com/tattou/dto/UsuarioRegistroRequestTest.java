package com.tattou.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class UsuarioRegistroRequestTest {

    @InjectMocks
    private UsuarioRegistroRequest usuarioRegistroRequest;

    @Test
    public void setNombreTest() {
        String nombre = "nombre";
        usuarioRegistroRequest.setNombre(nombre);
        Assert.isTrue(nombre.equals(usuarioRegistroRequest.getNombre()), "error");
    }

    @Test
    public void setApellidosTest() {
        String apellidos = "apellidos";
        usuarioRegistroRequest.setApellidos(apellidos);
        Assert.isTrue(apellidos.equals(usuarioRegistroRequest.getApellidos()), "error");
    }

    @Test
    public void setEmailTest() {
        String email = "email";
        usuarioRegistroRequest.setEmail(email);
        Assert.isTrue(email.equals(usuarioRegistroRequest.getEmail()), "error");
    }

    @Test
    public void setPasswordTest() {
        String password = "password";
        usuarioRegistroRequest.setPassword(password);
        Assert.isTrue(password.equals(usuarioRegistroRequest.getPassword()), "error");
    }

}
