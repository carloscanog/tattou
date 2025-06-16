package com.tattou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.tattou.service.StripeService;
import com.stripe.model.checkout.Session;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = "http://localhost:4200")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestBody Map<String, Object> body,
            HttpServletRequest request) throws StripeException {

        Long disenyoId = Long.valueOf(body.get("disenyoId").toString());
        String successUrl = "http://localhost:4200/compra-exitosa"; // URL de compra exitosa
        String cancelUrl = "http://localhost:4200/compra-cancelada"; // URL de compra cancelada

        // Crea la sesion con el id del disenyo y las url de compra exitosa y cancelada
        Session session = stripeService.createCheckoutSession(disenyoId, successUrl, cancelUrl);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", session.getUrl());

        return ResponseEntity.ok(responseData);
    }
    
}
