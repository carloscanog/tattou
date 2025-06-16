package com.tattou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tattou.model.Disenyo;
import com.tattou.repository.DisenyoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {

    @Autowired
    private DisenyoRepository disenyoRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public Session createCheckoutSession(Long disenyoId, String successUrl, String cancelUrl) throws StripeException {
        // Recupera los datos del diseño
        Disenyo disenyo = disenyoRepository.findById(disenyoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diseño no encontrado"));

        List<SessionCreateParams.LineItem> lineItems = List.of(
            SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount((long) (disenyo.getPrecio() * 100)) // Se multiplica por 100 ya que lo toma como centimos
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(disenyo.getTitulo())
                                .build()
                        )
                        .build()
                )
                .setQuantity(1L)
                .build()
        );

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .setCancelUrl(cancelUrl)
            .addAllLineItem(lineItems)
            .putMetadata("disenyoId", String.valueOf(disenyoId))
            .build();

        return Session.create(params);
    }
}

