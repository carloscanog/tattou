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

import com.tattou.model.Tatuaje;
import com.tattou.repository.TatuajeRepository;

@ExtendWith(MockitoExtension.class)
public class TatuajeServiceImplTest {

    @InjectMocks
    private TatuajeServiceImpl tatuajeServiceImpl;

    @Mock
    private TatuajeRepository tatuajeRepository;

    @Test
    public void obtenerTatuajesTest() {
        List<Tatuaje> tatuajes = List.of(new Tatuaje());
        when(tatuajeRepository.findAll()).thenReturn(tatuajes);
        List<Tatuaje> result = tatuajeServiceImpl.obtenerTatuajes();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerTatuajePorIdTest() {
        Long id = 1L;
        Optional<Tatuaje> tatuajeOpt = Optional.of(new Tatuaje());
        when(tatuajeRepository.findById(id)).thenReturn(tatuajeOpt);
        Optional<Tatuaje> result = tatuajeServiceImpl.obtenerTatuajePorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerPorAutorIdTest() {
        Long id = 1L;
        List<Tatuaje> tatuajeOpt = List.of(new Tatuaje());
        when(tatuajeRepository.findByAutorId(id)).thenReturn(tatuajeOpt);
        List<Tatuaje> result = tatuajeServiceImpl.obtenerPorAutorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void guardarTatuajeTest() {
        Tatuaje tatuaje = new Tatuaje();
        when(tatuajeRepository.save(Mockito.any(Tatuaje.class))).thenReturn(tatuaje);
        Tatuaje result = tatuajeServiceImpl.guardarTatuaje(tatuaje);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarTatuajePorIdTest() {
        Long id = 1L;
        tatuajeServiceImpl.eliminarTatuajePorId(id);
        Assert.notNull(tatuajeServiceImpl, "error");
    }

}
