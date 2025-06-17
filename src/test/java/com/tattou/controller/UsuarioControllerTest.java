package com.tattou.controller;

import static org.mockito.Mockito.when;

import java.util.List;
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

import com.tattou.dto.UsuarioRegistroRequest;
import com.tattou.model.Usuario;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void obtenerTodosTest() {
        Usuario usuario = new Usuario();
        List<Usuario> usuarios = List.of(usuario);

        when(usuarioService.obtenerUsuarios()).thenReturn(usuarios);

        List<Usuario> result = usuarioController.obtenerTodos();
        Assert.notNull(result, "error");
    }

    @Test
    public void registrarUsuarioTest() {
        UsuarioRegistroRequest dto = new UsuarioRegistroRequest();
        dto.setNombre("nombre");
        dto.setApellidos("apellidos");
        dto.setEmail("email");
        dto.setPassword("password");

        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioOpt = Optional.empty();

        when(usuarioService.obtenerUsuarioPorEmail("email")).thenReturn(usuarioOpt);

        String passEncode = "passEncode";
        when(passwordEncoder.encode("password")).thenReturn(passEncode);

        when(usuarioService.guardar(Mockito.any(Usuario.class))).thenReturn(usuario);
        ResponseEntity<?> result = usuarioController.registrarUsuario(dto);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPorIdTest() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioOpt = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorId(id)).thenReturn(usuarioOpt);
        Optional<Usuario> result = usuarioController.obtenerPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarTest() {
        Long id = 1L;
        usuarioController.eliminar(id);
        Assert.notNull(usuarioController, "error");
    }

    @Test
    public void eliminarUsuarioPorEmailTest() {
        String email = "email";
        usuarioController.eliminarUsuarioPorEmail(email);
        Assert.notNull(usuarioController, "error");
    }

}
