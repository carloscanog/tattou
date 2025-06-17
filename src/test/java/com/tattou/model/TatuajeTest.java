package com.tattou.model;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class TatuajeTest {

    @InjectMocks
    private Tatuaje tatuaje;

    @Test
    public void setIdTest() {
        Long id = 1L;
        tatuaje.setId(id);
        Assert.isTrue(id.equals(tatuaje.getId()), "error");
    }

    @Test
    public void setTituloTest() {
        String titulo = "titulo";
        tatuaje.setTitulo(titulo);
        Assert.isTrue(titulo.equals(tatuaje.getTitulo()), "error");
    }

    @Test
    public void setEtiquetasTest() {
        List<String> etiquetas = List.of("hola", "adios");
        tatuaje.setEtiquetas(etiquetas);
        Assert.isTrue(etiquetas.equals(tatuaje.getEtiquetas()), "error");
    }

    @Test
    public void setImagenTest() {
        String imagen = "imagen";
        tatuaje.setImagen(imagen);
        Assert.isTrue(imagen.equals(tatuaje.getImagen()), "error");
    }

    @Test
    public void setAutorTest() {
        Tatuador autor = new Tatuador();
        tatuaje.setAutor(autor);
        Assert.isTrue(autor.equals(tatuaje.getAutor()), "error");
    }

}
