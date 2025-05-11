package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.entities.Order;
import com.bydefault.store.entities.OrderItem;
import com.bydefault.store.entities.OrderStatus;
import com.bydefault.store.entities.User;
import com.bydefault.store.repositories.CartRepository;
import com.bydefault.store.repositories.OrderRepository;
import com.bydefault.store.repositories.UserRepository;
import com.bydefault.store.services.CartService;
import com.bydefault.store.services.CheckoutServices;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class CheckoutServicesImpl implements CheckoutServices {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CommonServiceImpl commonService;




    @Override
    public CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) {
        var cart = cartRepository.getCartWithItems(checkoutRequestDto.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        var order = new Order();
        order.setCustomer(commonService.getCurrentUser());
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        });
        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponseDto(order.getId());
    }
}
