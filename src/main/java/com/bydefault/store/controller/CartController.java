package com.bydefault.store.controller;


import com.bydefault.store.dtos.AddItemToCartRequest;
import com.bydefault.store.dtos.CartDto;
import com.bydefault.store.dtos.CartItemDto;
import com.bydefault.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/cart/")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = cartService.createCart();
        var uri = uriBuilder.path("/api/v1/cart/{id}/").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("{cartId}/items/")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @Valid @RequestBody AddItemToCartRequest request) {
        var cart = cartService.addItemToCart(cartId, request);

        return ResponseEntity.status(CREATED).body(cart);
    }

}
