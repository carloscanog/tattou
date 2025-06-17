package com.tattou.model;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class DisenyoTest {

    @InjectMocks
    private Disenyo disenyo;

    @Test
    public void setIdTest() {
        Long id = 1L;
        disenyo.setId(id);
        Assert.isTrue(id.equals(disenyo.getId()), "error");
    }

    @Test
    public void setTituloTest() {
        String titulo = "titulo";
        disenyo.setTitulo(titulo);
        Assert.isTrue(titulo.equals(disenyo.getTitulo()), "error");
    }

    @Test
    public void setEtiquetasTest() {
        List<String> etiquetas = List.of("hola", "adios");
        disenyo.setEtiquetas(etiquetas);
        Assert.isTrue(etiquetas.equals(disenyo.getEtiquetas()), "error");
    }

    @Test
    public void setPrecioTest() {
        Float precio = 1f;
        disenyo.setPrecio(precio);
        Assert.isTrue(precio.equals(disenyo.getPrecio()), "error");
    }

    @Test
    public void setImagenTest() {
        String imagen = "imagen";
        disenyo.setImagen(imagen);
        Assert.isTrue(imagen.equals(disenyo.getImagen()), "error");
    }

    @Test
    public void setAutorTest() {
        Tatuador autor = new Tatuador();
        disenyo.setAutor(autor);
        Assert.isTrue(autor.equals(disenyo.getAutor()), "error");
    }

}
