package com.bydefault.store.services;

import com.bydefault.store.dtos.AddItemToCartRequest;
import com.bydefault.store.dtos.CartDto;
import com.bydefault.store.dtos.CartItemDto;

import java.util.UUID;

public interface CartService {
    CartDto createCart();
    CartItemDto addItemToCart(UUID cartId, AddItemToCartRequest request);
}
