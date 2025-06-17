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

import com.tattou.model.Tatuador;
import com.tattou.model.Tatuaje;
import com.tattou.model.Usuario;
import com.tattou.service.TatuadorService;
import com.tattou.service.TatuajeService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class TatuajeControllerTest {

    @InjectMocks
    private TatuajeController tatuajeController;

    @Mock
    private TatuajeService tatuajeService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TatuadorService tatuadorService;

    @Test
    public void obtenerTatuajesTest() {
        List<Tatuaje> lista = new ArrayList<>();
        lista.add(new Tatuaje());
        lista.add(new Tatuaje());

        Mockito.when(tatuajeService.obtenerTatuajes()).thenReturn(lista);

        ResponseEntity<?> result = tatuajeController.obtenerTatuajes();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuajePorIdTest() {
        Long id = 1L;
        Tatuaje tatuaje = new Tatuaje();
        Optional<Tatuaje> tatuajeOpt = Optional.of(tatuaje);

        Mockito.when(tatuajeService.obtenerTatuajePorId(id)).thenReturn(tatuajeOpt);

        Optional<Tatuaje> result = tatuajeController.obtenerTatuajePorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuajesPorAutorIdTest() {
        Long autorId = 1L;
        List<Tatuaje> lista = new ArrayList<>();
        lista.add(new Tatuaje());
        lista.add(new Tatuaje());

        Mockito.when(tatuajeService.obtenerPorAutorId(autorId)).thenReturn(lista);

        List<Tatuaje> result = tatuajeController.obtenerTatuajesPorAutorId(autorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarTatuajePorIdTest() {
        Long id = 1L;
        tatuajeController.eliminarTatuajePorId(id);
        Assert.notNull(tatuajeController, "error");
    }

    @Test
    public void crearTatuajeTest() {
        String titulo = "titulo";
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("email");
        String etiquetasStr = "hola,adios,hola";
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.getOriginalFilename()).thenReturn("imagen");
        InputStream input = mock(InputStream.class);
        try {
            when(imagen.getInputStream()).thenReturn(input);
        } catch (Exception e) {
            Assert.notNull(tatuajeController, "error");
        }
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Tatuador tatuador = new Tatuador();

        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        Optional<Tatuador> tatuadorOpt = Optional.of(tatuador);

        when(usuarioService.obtenerUsuarioPorEmail("email")).thenReturn(usuarioOpt);
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOpt);

        Tatuaje tatuaje = new Tatuaje();
        when(tatuajeService.guardarTatuaje(Mockito.any(Tatuaje.class))).thenReturn(tatuaje);

        ResponseEntity<?> result = tatuajeController.crearTatuaje(titulo, etiquetasStr, imagen, authentication);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuajesDelTatuadorAutenticadoTest() {
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

        Tatuaje tatuaje = new Tatuaje();
        List<Tatuaje> tatuajes = new ArrayList<>();
        tatuajes.add(tatuaje);

        when(tatuajeService.obtenerPorAutorId(1L)).thenReturn(tatuajes);

        ResponseEntity<?> result = tatuajeController.obtenerTatuajesDelTatuadorAutenticado();
        Assert.notNull(result, "error");
    }

}
