package com.tattou.controller;

import static org.mockito.Mockito.when;

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
import org.springframework.util.Assert;

import com.tattou.dto.TatuadorRegistroRequest;
import com.tattou.model.Cliente;
import com.tattou.model.Disenyo;
import com.tattou.model.Pedido;
import com.tattou.model.Tatuador;
import com.tattou.model.Tatuaje;
import com.tattou.model.Usuario;
import com.tattou.service.ClienteService;
import com.tattou.service.DisenyoService;
import com.tattou.service.PedidoService;
import com.tattou.service.TatuadorService;
import com.tattou.service.TatuajeService;
import com.tattou.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class TatuadorControllerTest {

    @InjectMocks
    private TatuadorController tatuadorController;

    @Mock
    private TatuadorService tatuadorService;

    @Mock
    private TatuajeService tatuajeService;

    @Mock
    private DisenyoService disenyoService;
    
    @Mock
    private PedidoService pedidoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ClienteService clienteService;

    @Test
    public void obtenerTodosTest() {
        Tatuador tatuador = new Tatuador();
        List<Tatuador> tatuadores = List.of(tatuador);

        when(tatuadorService.obtenerTatuadores()).thenReturn(tatuadores);

        List<Tatuador> result = tatuadorController.obtenerTodos();
        Assert.notNull(result, "error");
    }

    @Test
    public void completarRegistroTatuadorTest() {
        TatuadorRegistroRequest dto = new TatuadorRegistroRequest();
        dto.setUsuarioId(1L);
        dto.setBiografia("biografia");
        dto.setEstilos(List.of("hola", "adios"));
        dto.setInstagram("instagram");
        dto.setTiktok("tiktok");;
        dto.setUbicacion("ubi");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Optional<Usuario> usuarioOpt = Optional.of(usuario);

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuarioOpt);
        Optional<Tatuador> tatuadorOpt = Optional.empty();
        Optional<Cliente> clienteOpt = Optional.empty();
        when(tatuadorService.obtenerTatuadorPorUsuarioId(1L)).thenReturn(tatuadorOpt);
        when(clienteService.obtenerClientePorUsuarioId(1L)).thenReturn(clienteOpt);

        Tatuador tatuador = new Tatuador();
        when(tatuadorService.guardar(Mockito.any(Tatuador.class))).thenReturn(tatuador);

        ResponseEntity<Tatuador> result = tatuadorController.completarRegistroTatuador(dto);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuajesPorAutorIdTest() {
        Long id = 1L;
        List<Tatuaje> tatuajes = new ArrayList<>();
        tatuajes.add(new Tatuaje());
        when(tatuajeService.obtenerPorAutorId(id)).thenReturn(tatuajes);

        ResponseEntity<?> result = tatuadorController.obtenerTatuajesPorAutorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerDisenyosPorAutorIdTest() {
        Long id = 1L;
        List<Disenyo> disenyos = new ArrayList<>();
        disenyos.add(new Disenyo());
        when(disenyoService.obtenerDisenyosPorAutorId(id)).thenReturn(disenyos);

        ResponseEntity<?> result = tatuadorController.obtenerDisenyosPorAutorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidosPorVendedorIdTest() {
        Long id = 1L;
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido());
        when(pedidoService.obtenerPedidosPorVendedorId(id)).thenReturn(pedidos);

        List<Pedido> result = tatuadorController.obtenerPedidosPorVendedorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPorIdTest() {
        Long id = 1L;
        Tatuador tatuador = new Tatuador();
        Optional<Tatuador> tatuadorOpt = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorId(id)).thenReturn(tatuadorOpt);

        Optional<Tatuador> result = tatuadorController.obtenerPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPedidoPorIdYVendedorIdTest() {
        Long pedidoId = 1L;
        Long vendedorId = 1L;
        Pedido pedido = new Pedido();
        Optional<Pedido> pedidoOpt = Optional.of(pedido);
        when(pedidoService.obtenerPedidoPorIdYVendedorId(pedidoId, vendedorId)).thenReturn(pedidoOpt);

        Optional<Pedido> result = tatuadorController.obtenerPedidoPorIdYVendedorId(vendedorId, pedidoId);
        Assert.notNull(result, "error");
    }

    @Test
    public void crearTest() {
        Tatuador tatuador = new Tatuador();
        when(tatuadorService.guardar(Mockito.any(Tatuador.class))).thenReturn(tatuador);

        Tatuador result = tatuadorController.crear(tatuador);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarTest() {
        Long id = 1L;
        tatuadorController.eliminar(id);
        Assert.notNull(tatuadorController, "error");
    }

    @Test
    public void actualizarTatuadorTest() {
        Long id = 1L;
        Tatuador tatuador = new Tatuador();
        tatuador.setBiografia("biografia");
        tatuador.setEstilos(List.of("hola", "adios"));
        tatuador.setInstagram("instagram");
        tatuador.setTiktok("tiktok");;
        tatuador.setUbicacion("ubi");

        Optional<Tatuador> tatuadorOpt = Optional.of(tatuador);
        when(tatuadorService.obtenerTatuadorPorId(id)).thenReturn(tatuadorOpt);
        when(tatuadorService.guardar(tatuador)).thenReturn(tatuador);

        ResponseEntity<Tatuador> result = tatuadorController.actualizarTatuador(id, tatuador);
        Assert.notNull(result, "error");
    }

}
