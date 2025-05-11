package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.dtos.checkout.WebhookRequest;
import com.bydefault.store.entities.Order;
import com.bydefault.store.entities.OrderItem;
import com.bydefault.store.entities.PaymentStatus;
import com.bydefault.store.exceptions.PaymentGatewayException;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.CartRepository;
import com.bydefault.store.repositories.OrderRepository;
import com.bydefault.store.services.CartService;
import com.bydefault.store.services.CheckoutServices;
import com.bydefault.store.services.PaymentGateServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CheckoutServicesImpl implements CheckoutServices {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CommonServiceImpl commonService;
    private final PaymentGateServices paymentGateServices;


    @Override
    public CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) {
        var cart = cartRepository.getCartWithItems(checkoutRequestDto.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        var order = new Order();
        order.setCustomer(commonService.getCurrentUser());
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(PaymentStatus.PENDING);
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

        try {

//
            var session = paymentGateServices.createCheckoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponseDto(order.getId(), session.getCheckoutUrl());

        } catch (PaymentGatewayException e) {
            orderRepository.delete(order);
            throw new PaymentGatewayException(e.getMessage());
        }

    }

    @Override
    public void handleWebhookEvent(WebhookRequest request) {
      paymentGateServices.parseWebhookRequest(request).ifPresent(webhook -> {
          var order = orderRepository.findById(webhook.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
          order.setStatus(webhook.getPaymentStatus());
          orderRepository.save(order);
      });
    }
}
