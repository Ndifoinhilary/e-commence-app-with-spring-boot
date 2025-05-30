package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.cart.AddItemToCartRequest;
import com.bydefault.store.dtos.cart.CartDto;
import com.bydefault.store.dtos.cart.CartItemDto;
import com.bydefault.store.dtos.cart.UpdateCartItems;
import com.bydefault.store.entities.CartItems;
import com.bydefault.store.entities.Carts;
import com.bydefault.store.entities.mappers.CartMapper;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.CartRepository;
import com.bydefault.store.repositories.ProductRepository;
import com.bydefault.store.repositories.UserRepository;
import com.bydefault.store.services.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class CartServicesImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    @Override
    public CartDto createCart() {
        var cart = new Carts();
        log.atInfo().log("Creating new cart.");
        cart = cartRepository.save(cart);
        log.atInfo().log("Saved new cart.");
        return cartMapper.toDto(cart);
    }

    @Override
    public CartItemDto addItemToCart(UUID cartId, AddItemToCartRequest request) {
        var cart = getCarts(cartId);
        var product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("No product with the given id found"));
        var cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    @Override
    public CartDto getCart(UUID cartId) {
        var cart = getCarts(cartId);
        return cartMapper.toDto(cart);
    }

    @Override
    public CartItemDto updateCart(UUID cartId, Long productId, UpdateCartItems updateCartItems) {
        var cart = getCarts(cartId);
        var cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product not in cart"));
        cartItem.setQuantity(updateCartItems.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    @Override
    public void removeItemFromCart(UUID cartId, Long productId) {
        var cart = getCarts(cartId);
        var cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product not in cart"));
        cart.getItems().remove(cartItem);
        cartItem.setCart(null);
        cartRepository.save(cart);


    }

    @Override
    public void clearCart(UUID cartId) {
        var cart = getCarts(cartId);
        cart.getItems().clear();
    }

    private Carts getCarts(UUID cartId) {
        return cartRepository.getCartWithItems(cartId).orElseThrow(() -> new ResourceNotFoundException("No cart with the given id found"));

    }
}
