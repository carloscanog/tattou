package com.tattou.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.tattou.dto.LoginRequest;
import com.tattou.model.Usuario;
import com.tattou.security.JwUtil;
import com.tattou.service.UsuarioService;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwUtil jwUtil;

    @Test
    public void loginTest() {
        LoginRequest login = new LoginRequest();
        login.setEmail("email");
        login.setPassword("password");

        Usuario usuario = new Usuario();
        usuario.setPassword("password");
        Optional<Usuario> usuarioOpt = Optional.of(usuario);

        Mockito.when(usuarioService.obtenerUsuarioPorEmail(Mockito.anyString())).thenReturn(usuarioOpt);
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        String token = "token";
        Mockito.when(jwUtil.generateToken(usuario.getEmail())).thenReturn(token);

        ResponseEntity<?> response = authController.login(login);
        Assert.notNull(response, "error");
    }

}
