package com.tattou.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.tattou.service.PedidoService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = "http://localhost:4200")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final PedidoService pedidoService;

    public StripeWebhookController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
        @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            // Se verifica que el evento venga de Stripe usando la firma secreta
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            // Si la firma no es valida, se rechaza la peticion
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma no válida");
        }

        // Si se ha completado la sesion de pago, se procede a crear el pedido
        if ("checkout.session.completed".equals(event.getType())) {
            EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

            try {
                Session session;

                if (deserializer.getObject().isPresent()) {
                    // En caso de que Stripe envie la sesion de pago completa, se usa directamente
                    session = (Session) deserializer.getObject().get();
                } else {
                    // Si no se ha podido deserializar, se recupera la sesion manualmente usando el ID
                    String rawJson = deserializer.getRawJson();
                    JsonObject raw = JsonParser.parseString(rawJson).getAsJsonObject();
                    String sessionId = raw.get("id").getAsString();

                    session = Session.retrieve(sessionId);
                }

                // Se obtiene el id del disenyo desde los metadatos enviados
                String disenyoId = session.getMetadata().get("disenyoId");
                // Se obtiene el email del cliente desde los metadatos enviados
                String clienteEmail = session.getCustomerDetails().getEmail();

                // Llamada al servicio encargado de la creacion del pedido
                pedidoService.crearPedidoTrasPagoExitoso(Long.valueOf(disenyoId), clienteEmail);

            } catch (Exception e) {
                System.out.println("Error al procesar el evento: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Se devuelve una respuesta vacia indicando que se ha recibido correctamente el webhook
        return ResponseEntity.ok("");
    }
}
