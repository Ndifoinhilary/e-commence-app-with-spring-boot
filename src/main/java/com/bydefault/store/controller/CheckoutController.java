package com.bydefault.store.controller;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.dtos.checkout.WebhookRequest;
import com.bydefault.store.exceptions.PaymentGatewayException;
import com.bydefault.store.services.CheckoutServices;
import com.bydefault.store.services.PaymentGateServices;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/checkout/")
@AllArgsConstructor
@Log4j2
public class CheckoutController {

    private final CheckoutServices checkoutServices;
    @PostMapping
    public ResponseEntity<CheckoutResponseDto> checkout(@Valid @RequestBody CheckoutRequestDto checkoutRequestDto) {
        try {
            log.info("Received checkout request for cart ID: {}", checkoutRequestDto.getCartId());
            return ResponseEntity.ok(checkoutServices.checkout(checkoutRequestDto));
        } catch (StripeException e) {
            log.error("Stripe error during checkout: {}", e.getMessage(), e);
            throw new PaymentGatewayException("Sorry something went wrong. Please try again later");
        }
    }

    @PostMapping("webhook/")
    public void handleWebhook(@RequestHeader Map<String, String> headers, String payload) {
        checkoutServices.handleWebhookEvent(new WebhookRequest(headers, payload));
    }
}
