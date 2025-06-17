package com.tattou.dto;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class TatuadorRegistroRequestTest {

    @InjectMocks
    private TatuadorRegistroRequest tatuadorRegistroRequest;

    @Test
    public void setUsuarioIdTest() {
        Long usuarioId = 1L;
        tatuadorRegistroRequest.setUsuarioId(usuarioId);
        Assert.isTrue(usuarioId.equals(tatuadorRegistroRequest.getUsuarioId()), "error");
    }

    @Test
    public void setUbicacionTest() {
        String ubicacion = "ubicacion";
        tatuadorRegistroRequest.setUbicacion(ubicacion);
        Assert.isTrue(ubicacion.equals(tatuadorRegistroRequest.getUbicacion()), "error");
    }

    @Test
    public void setEstilosTest() {
        List<String> estilos = List.of("hola", "adios");
        tatuadorRegistroRequest.setEstilos(estilos);
        Assert.isTrue(estilos.equals(tatuadorRegistroRequest.getEstilos()), "error");
    }

    @Test
    public void setInstagramTest() {
        String instagram = "instagram";
        tatuadorRegistroRequest.setInstagram(instagram);
        Assert.isTrue(instagram.equals(tatuadorRegistroRequest.getInstagram()), "error");
    }

    @Test
    public void setTiktokTest() {
        String tiktok = "tiktok";
        tatuadorRegistroRequest.setTiktok(tiktok);
        Assert.isTrue(tiktok.equals(tatuadorRegistroRequest.getTiktok()), "error");
    }

}
