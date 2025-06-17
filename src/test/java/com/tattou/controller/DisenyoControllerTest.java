package com.tattou.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.tattou.model.Disenyo;
import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;
import com.tattou.service.DisenyoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class DisenyoControllerTest {

    @InjectMocks
    private DisenyoController disenyoController;

    @Mock
    private DisenyoService disenyoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TatuadorService tatuadorService;

    @Test
    public void obtenerDisenyosTest() {
        List<Disenyo> lista = new ArrayList<>();
        lista.add(new Disenyo());
        lista.add(new Disenyo());

        Mockito.when(disenyoService.obtenerDisenyos()).thenReturn(lista);

        ResponseEntity<?> result = disenyoController.obtenerDisenyos();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerDisenyoPorIdTest() {
        Long id = 1L;
        Disenyo disenyo = new Disenyo();
        Optional<Disenyo> disenyoOpt = Optional.of(disenyo);

        Mockito.when(disenyoService.obtenerDisenyoPorId(id)).thenReturn(disenyoOpt);

        Optional<Disenyo> result = disenyoController.obtenerDisenyoPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearDisenyoTest() {
        String titulo = "titulo";
        Float precio = 1f;
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("email");
        String etiquetasStr = "hola,adios,hola";
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.getOriginalFilename()).thenReturn("imagen");
        InputStream input = mock(InputStream.class);
        try {
            when(imagen.getInputStream()).thenReturn(input);
        } catch (Exception e) {
            Assert.notNull(disenyoController, "error");
        }
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Tatuador tatuador = new Tatuador();

        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        Optional<Tatuador> tatuadorOpt = Optional.of(tatuador);

        when(usuarioService.obtenerUsuarioPorEmail("email")).thenReturn(usuarioOpt);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOpt);

        Disenyo disenyo = new Disenyo();
        when(disenyoService.guardarDisenyo(Mockito.any(Disenyo.class))).thenReturn(disenyo);

        ResponseEntity<?> result = disenyoController.crearDisenyo(titulo, etiquetasStr, imagen, precio, authentication);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarDisenyoPorIdTest() {
        disenyoController.eliminarDisenyoPorId(1L);
        Assert.notNull(disenyoController, "error");
    }

    @Test
    public void obtenerDisenyossDelTatuadorAutenticadoTest() {
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
        when(usuarioService.esRegistradoComoCliente(usuario)).thenReturn(false);

        Tatuador tatuador = new Tatuador();
        tatuador.setId(1L);
        Optional<Tatuador> tatuadorOptional = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOptional);

        Disenyo disenyo = new Disenyo();
        List<Disenyo> disenyos = new ArrayList<>();
        disenyos.add(disenyo);

        when(disenyoService.obtenerDisenyosPorAutorId(1L)).thenReturn(disenyos);

        ResponseEntity<?> result = disenyoController.obtenerDisenyossDelTatuadorAutenticado();
        Assert.notNull(result, "error");
    }

}
