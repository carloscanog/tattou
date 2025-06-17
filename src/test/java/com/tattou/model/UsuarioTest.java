package com.tattou.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class UsuarioTest {

    @InjectMocks
    private Usuario usuario;

    @Test
    public void setIdTest() {
        Long id = 1L;
        usuario.setId(id);
        Assert.isTrue(id.equals(usuario.getId()), "error");
    }

    @Test
    public void setEmailTest() {
        String email = "email";
        usuario.setEmail(email);
        Assert.isTrue(email.equals(usuario.getEmail()), "error");
    }

    @Test
    public void setPasswordTest() {
        String password = "password";
        usuario.setPassword(password);
        Assert.isTrue(password.equals(usuario.getPassword()), "error");
    }

    @Test
    public void setNombreTest() {
        String nombre = "nombre";
        usuario.setNombre(nombre);
        Assert.isTrue(nombre.equals(usuario.getNombre()), "error");
    }

    @Test
    public void setApellidosTest() {
        String apellidos = "apellidos";
        usuario.setApellidos(apellidos);
        Assert.isTrue(apellidos.equals(usuario.getApellidos()), "error");
    }

    @Test
    public void setFotoPerfilTest() {
        String fotoPerfil = "fotoPerfil";
        usuario.setFotoPerfil(fotoPerfil);
        Assert.isTrue(fotoPerfil.equals(usuario.getFotoPerfil()), "error");
    }

}
