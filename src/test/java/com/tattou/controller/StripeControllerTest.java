package com.tattou.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.stripe.model.checkout.Session;
import com.tattou.service.StripeService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class StripeControllerTest {

    @InjectMocks
    private StripeController stripeController;

    @Mock
    private StripeService stripeService;

    @Test
    public void createCheckoutSessionTest() {
        Map<String, Object> body = new HashMap<>();
        body.put("disenyoId", 1L);

        Session session = mock(Session.class);
        try {
            when(stripeService.createCheckoutSession(1L,
                "http://localhost:4200/compra-exitosa",
                "http://localhost:4200/compra-cancelada")).thenReturn(session);
        } catch (Exception e) {
            Assert.notNull(stripeController, "error");
        }

        HttpServletRequest request = mock(HttpServletRequest.class);

        Object result = null;

        try {
            result = stripeController.createCheckoutSession(body, request);
        } catch (Exception e) {
            Assert.notNull(stripeController, "error");
        }

        Assert.notNull(result, "error");
    }

}
