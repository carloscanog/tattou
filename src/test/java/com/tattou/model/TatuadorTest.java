package com.tattou.model;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class TatuadorTest {

    @InjectMocks
    private Tatuador tatuador;

    @Test
    public void setIdTest() {
        Long id = 1L;
        tatuador.setId(id);
        Assert.isTrue(id.equals(tatuador.getId()), "error");
    }

    @Test
    public void setUsuarioTest() {
        Usuario usuario = new Usuario();
        tatuador.setUsuario(usuario);
        Assert.isTrue(usuario.equals(tatuador.getUsuario()), "error");
    }

    @Test
    public void setBiografiaTest() {
        String biografia = "biografia";
        tatuador.setBiografia(biografia);
        Assert.isTrue(biografia.equals(tatuador.getBiografia()), "error");
    }

    @Test
    public void setUbicacionTest() {
        String ubicacion = "ubicacion";
        tatuador.setUbicacion(ubicacion);
        Assert.isTrue(ubicacion.equals(tatuador.getUbicacion()), "error");
    }

    @Test
    public void setEtiquetasTest() {
        List<String> estilos = List.of("hola", "adios");
        tatuador.setEstilos(estilos);
        Assert.isTrue(estilos.equals(tatuador.getEstilos()), "error");
    }

    @Test
    public void setInstagramTest() {
        String instagram = "instagram";
        tatuador.setInstagram(instagram);
        Assert.isTrue(instagram.equals(tatuador.getInstagram()), "error");
    }

    @Test
    public void setTiktokTest() {
        String tiktok = "tiktok";
        tatuador.setTiktok(tiktok);
        Assert.isTrue(tiktok.equals(tatuador.getTiktok()), "error");
    }

}
