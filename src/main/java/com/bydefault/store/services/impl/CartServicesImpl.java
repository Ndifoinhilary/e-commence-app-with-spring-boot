package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.AddItemToCartRequest;
import com.bydefault.store.dtos.CartDto;
import com.bydefault.store.dtos.CartItemDto;
import com.bydefault.store.entities.CartItems;
import com.bydefault.store.entities.Carts;
import com.bydefault.store.entities.mappers.CartMapper;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.CartRepository;
import com.bydefault.store.repositories.ProductRepository;
import com.bydefault.store.repositories.UserRepository;
import com.bydefault.store.services.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("No cart with the given id found"));
        var product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("No product with the given id found"));
        var cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }
}
