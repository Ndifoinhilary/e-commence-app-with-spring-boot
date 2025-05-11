package com.bydefault.store.controller;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.exceptions.SystemInternalError;
import com.bydefault.store.services.CheckoutServices;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
         throw new SystemInternalError("Sorry something went wrong. Please try again later");
     }
    }


}
