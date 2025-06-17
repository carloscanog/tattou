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

import com.tattou.model.Disenyo;
import com.tattou.repository.DisenyoRepository;

@ExtendWith(MockitoExtension.class)
public class DisenyoServiceImplTest {

    @InjectMocks
    private DisenyoServiceImpl disenyoServiceImpl;

    @Mock
    private DisenyoRepository disenyoRepository;

    @Test
    public void obtenerDisenyosTest() {
        List<Disenyo> disenyos = List.of(new Disenyo());
        when(disenyoRepository.findAll()).thenReturn(disenyos);
        List<Disenyo> result = disenyoServiceImpl.obtenerDisenyos();
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerDisenyoPorIdTest() {
        Long id = 1L;
        Optional<Disenyo> disenyoOpt = Optional.of(new Disenyo());
        when(disenyoRepository.findById(id)).thenReturn(disenyoOpt);
        Optional<Disenyo> result = disenyoServiceImpl.obtenerDisenyoPorId(id);
        Assert.notNull(result, "error");
    }

    @Test
    public void obtenerDisenyosPorAutorIdTest() {
        Long autorId = 1L;
        List<Disenyo> disenyoOpt = List.of(new Disenyo());
        when(disenyoRepository.findByAutorId(autorId)).thenReturn(disenyoOpt);
        List<Disenyo> result = disenyoServiceImpl.obtenerDisenyosPorAutorId(autorId);
        Assert.notNull(result, "error");
    }

    @Test
    public void guardarDisenyoTest() {
        Disenyo disenyo = new Disenyo();
        when(disenyoRepository.save(Mockito.any(Disenyo.class))).thenReturn(disenyo);
        Disenyo result = disenyoServiceImpl.guardarDisenyo(disenyo);
        Assert.notNull(result, "error");
    }

    @Test
    public void eliminarDisenyoPorIdTest() {
        Long id = 1L;
        disenyoServiceImpl.eliminarDisenyoPorId(id);
        Assert.notNull(disenyoServiceImpl, "error");
    }

}
