package com.bydefault.store.controller;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.services.CheckoutServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checkout/")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutServices checkoutServices;

    @PostMapping
    public ResponseEntity<CheckoutResponseDto> checkout(@Valid @RequestBody CheckoutRequestDto checkoutRequestDto) {
        return ResponseEntity.ok(checkoutServices.checkout(checkoutRequestDto));
    }

    
}
