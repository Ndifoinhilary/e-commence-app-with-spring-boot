package com.bydefault.store.controller;


import com.bydefault.store.dtos.cart.AddItemToCartRequest;
import com.bydefault.store.dtos.cart.CartDto;
import com.bydefault.store.dtos.cart.CartItemDto;
import com.bydefault.store.dtos.cart.UpdateCartItems;
import com.bydefault.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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

    @GetMapping("{cartId}/")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @PutMapping("{cartId}/update/{productId}/")
    public ResponseEntity<CartItemDto> updateCart(@PathVariable UUID cartId, @PathVariable Long productId, @Valid @RequestBody UpdateCartItems updateCartItems){
        return ResponseEntity.ok(cartService.updateCart(cartId, productId, updateCartItems));
    }

    @PostMapping("{cartId}/remove/{productId}/")
    public ResponseEntity<String> removeFromCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.status(NO_CONTENT).body("Product " + productId + " removed from cart.");
    }

    @DeleteMapping("{cartId}/")
    public ResponseEntity<String> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.status(NO_CONTENT).body("Cart has been cleared.");
    }
}
