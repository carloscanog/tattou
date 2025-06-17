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

import com.tattou.model.Tatuador;
import com.tattou.repository.TatuadorRepository;

@ExtendWith(MockitoExtension.class)
public class TatuadorServiceImplTest {

    @InjectMocks
    private TatuadorServiceImpl tatuadorServiceImpl;

    @Mock
    private TatuadorRepository tatuadorRepository;

    @Test
    public void obtenerTatuadoresTest() {
        List<Tatuador> tatuadores = List.of(new Tatuador());
        when(tatuadorRepository.findAll()).thenReturn(tatuadores);
        List<Tatuador> result = tatuadorServiceImpl.obtenerTatuadores();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuadorPorIdTest() {
        Long id = 1L;
        Optional<Tatuador> tatuadorOpt = Optional.of(new Tatuador());
        when(tatuadorRepository.findById(id)).thenReturn(tatuadorOpt);
        Optional<Tatuador> result = tatuadorServiceImpl.obtenerTatuadorPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuadorPorUsuarioIdTest() {
        Long usuarioId = 1L;
        Optional<Tatuador> tatuadorOpt = Optional.of(new Tatuador());
        when(tatuadorRepository.findByUsuarioId(usuarioId)).thenReturn(tatuadorOpt);
        Optional<Tatuador> result = tatuadorServiceImpl.obtenerTatuadorPorUsuarioId(usuarioId);
        Assert.notNull(result, "error");
    }

    @Test
    public void guardarTest() {
        Tatuador tatuador = new Tatuador();
        when(tatuadorRepository.save(Mockito.any(Tatuador.class))).thenReturn(tatuador);
        Tatuador result = tatuadorServiceImpl.guardar(tatuador);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarTatuadorPorIdTest() {
        Long id = 1L;
        tatuadorServiceImpl.eliminarTatuadorPorId(id);
        Assert.notNull(tatuadorServiceImpl, "error");
    }

}
