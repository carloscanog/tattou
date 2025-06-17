package com.tattou.controller;

import static org.mockito.Mockito.mock;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.tattou.model.Cliente;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class PerfilControllerTest {

    @InjectMocks
    private PerfilController perfilController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TatuadorService tatuadorService;

    @Mock
    private ClienteService clienteService;

    @Test
    public void getUsuarioActualTest1() {
        String email = "email@email.com";
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn(email);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setFotoPerfil("fotoPerfil");
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorEmail(email)).thenReturn(usuarioOptional);
        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(true);

        Tatuador tatuador = new Tatuador();
        tatuador.setId(1L);
        tatuador.setBiografia("biografia");
        tatuador.setUbicacion("ubicacion");
        tatuador.setEstilos(List.of("hola", "adios"));
        tatuador.setInstagram("instagram");
        tatuador.setTiktok("tiktok");

        Optional<Tatuador> tatuadorOptional = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOptional);

        ResponseEntity<?> result = perfilController.getUsuarioActual();
        Assert.notNull(result, "error");

        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(false);
        when(usuarioService.esRegistradoComoCliente(usuario)).thenReturn(true);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCiudad("ciudad");
        cliente.setIntereses(List.of("hola", "adios"));

        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteService.obtenerClientePorUsuarioId(1L)).thenReturn(clienteOptional);

        ResponseEntity<?> result2 = perfilController.getUsuarioActual();
        Assert.notNull(result2, "error");
    }

    @Test
    public void getUsuarioActualTest2() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("email@email.com");
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setFotoPerfil("fotoPerfil");
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuarioOptional);
        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(true);

        Tatuador tatuador = new Tatuador();
        tatuador.setId(1L);
        tatuador.setBiografia("biografia");
        tatuador.setUbicacion("ubicacion");
        tatuador.setEstilos(List.of("hola", "adios"));
        tatuador.setInstagram("instagram");
        tatuador.setTiktok("tiktok");

        Optional<Tatuador> tatuadorOptional = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOptional);

        ResponseEntity<?> result = perfilController.getUsuarioActual(1L);
        Assert.notNull(result, "error");

        when(usuarioService.esRegistradoComoTatuador(usuario)).thenReturn(false);
        when(usuarioService.esRegistradoComoCliente(usuario)).thenReturn(true);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCiudad("ciudad");
        cliente.setIntereses(List.of("hola", "adios"));

        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteService.obtenerClientePorUsuarioId(1L)).thenReturn(clienteOptional);

        ResponseEntity<?> result2 = perfilController.getUsuarioActual(1L);
        Assert.notNull(result2, "error");
    }

     @Test
    public void actualizarFotoPerfilTest() {
        MultipartFile archivo = mock(MultipartFile.class);
        when(archivo.getOriginalFilename()).thenReturn("nombre");
        byte[] arrays = new byte[10];
        try {
            when(archivo.getBytes()).thenReturn(arrays);
        } catch (Exception e) {
            Assert.notNull(perfilController, "error");
        }

        String email = "email@email.com";
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn(email);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorEmail(email)).thenReturn(usuarioOptional);
        when(usuarioService.guardar(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> result = perfilController.actualizarFotoPerfil(archivo);
        Assert.notNull(result, "error");
    }

}
