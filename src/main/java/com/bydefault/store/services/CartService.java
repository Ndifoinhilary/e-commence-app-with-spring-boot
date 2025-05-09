package com.bydefault.store.services;

import com.bydefault.store.dtos.cart.AddItemToCartRequest;
import com.bydefault.store.dtos.cart.CartDto;
import com.bydefault.store.dtos.cart.CartItemDto;
import com.bydefault.store.dtos.cart.UpdateCartItems;

import java.util.UUID;

public interface CartService {
    CartDto createCart();
    CartItemDto addItemToCart(UUID cartId, AddItemToCartRequest request);
    CartDto getCart(UUID cartId);
    CartItemDto updateCart(UUID cartId, Long productId, UpdateCartItems updateCartItems);
}
