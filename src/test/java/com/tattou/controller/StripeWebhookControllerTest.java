package com.tattou.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.tattou.service.PedidoService;

@ExtendWith(MockitoExtension.class)
public class StripeWebhookControllerTest {

    @InjectMocks
    private StripeWebhookController stripeWebhookController;

    @Mock
    private PedidoService pedidoService;

    @Test
    public void handleStripeWebhookTest() {
        Assert.notNull(stripeWebhookController, "error");
    }

}
