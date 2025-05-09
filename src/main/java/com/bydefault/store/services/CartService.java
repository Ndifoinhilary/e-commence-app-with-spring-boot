package com.bydefault.store.services;

import com.bydefault.store.dtos.cart.AddItemToCartRequest;
import com.bydefault.store.dtos.cart.CartDto;
import com.bydefault.store.dtos.cart.CartItemDto;

import java.util.UUID;

public interface CartService {
    CartDto createCart();
    CartItemDto addItemToCart(UUID cartId, AddItemToCartRequest request);
}
