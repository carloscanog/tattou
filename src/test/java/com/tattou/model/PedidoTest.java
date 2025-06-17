package com.tattou.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class PedidoTest {

    @InjectMocks
    private Pedido pedido;

    @Test
    public void setIdTest() {
        Long id = 1L;
        pedido.setId(id);
        Assert.isTrue(id.equals(pedido.getId()), "error");
    }

    @Test
    public void setCompradorTest() {
        Cliente comprador = new Cliente();
        pedido.setComprador(comprador);
        Assert.isTrue(comprador.equals(pedido.getComprador()), "error");
    }

    @Test
    public void setVendedorTest() {
        Tatuador vendedor = new Tatuador();
        pedido.setVendedor(vendedor);
        Assert.isTrue(vendedor.equals(pedido.getVendedor()), "error");
    }

    @Test
    public void setAutorTest() {
        Disenyo disenyo = new Disenyo();
        pedido.setDisenyo(disenyo);
        Assert.isTrue(disenyo.equals(pedido.getDisenyo()), "error");
    }

    @Test
    public void setFechaTest() {
        LocalDateTime fecha = LocalDateTime.now();
        pedido.setFecha(fecha);
        Assert.isTrue(fecha.equals(pedido.getFecha()), "error");
    }

}
