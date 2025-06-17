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

import com.tattou.model.Usuario;
import com.tattou.repository.ClienteRepository;
import com.tattou.repository.TatuadorRepository;
import com.tattou.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TatuadorRepository tatuadorRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    public void obtenerUsuariosTest() {
        List<Usuario> usuarios = List.of(new Usuario());
        when(usuarioRepository.findAll()).thenReturn(usuarios);
        List<Usuario> result = usuarioServiceImpl.obtenerUsuarios();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerUsuarioPorIdTest() {
        Long id = 1L;
        Optional<Usuario> usuarioOpt = Optional.of(new Usuario());
        when(usuarioRepository.findById(id)).thenReturn(usuarioOpt);
        Optional<Usuario> result = usuarioServiceImpl.obtenerUsuarioPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerUsuarioPorEmailTest() {
        String email = "email";
        Optional<Usuario> usuarioOpt = Optional.of(new Usuario());
        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioOpt);
        Optional<Usuario> result = usuarioServiceImpl.obtenerUsuarioPorEmail(email);
        Assert.notNull(result, "error");
    }

    @Test
    public void guardarTest() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Usuario result = usuarioServiceImpl.guardar(usuario);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarUsuarioPorIdTest() {
        Long id = 1L;
        usuarioServiceImpl.eliminarUsuarioPorId(id);
        Assert.notNull(usuarioServiceImpl, "error");
    }

    @Test
    public void esRegistradoComoTatuadorTest() {
        Usuario usuario = new Usuario();
        Boolean bool = true;
        when(tatuadorRepository.existsByUsuario(usuario)).thenReturn(bool);
        Boolean result = usuarioServiceImpl.esRegistradoComoTatuador(usuario);
        Assert.notNull(result, "error");
    }

    @Test
    public void esRegistradoComoClienteTest() {
        Usuario usuario = new Usuario();
        Boolean bool = true;
        when(clienteRepository.existsByUsuario(usuario)).thenReturn(bool);
        Boolean result = usuarioServiceImpl.esRegistradoComoCliente(usuario);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarUsuarioPorEmailTest() {
        String email = "email";
        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioOpt);
        when(tatuadorRepository.existsByUsuario(usuario)).thenReturn(false);
        when(clienteRepository.existsByUsuario(usuario)).thenReturn(false);
        usuarioServiceImpl.eliminarUsuarioPorEmail(email);
        Assert.notNull(usuarioServiceImpl, "error");
    }

}
