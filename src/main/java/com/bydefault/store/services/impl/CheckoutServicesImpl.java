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
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CheckoutServicesImpl implements CheckoutServices {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CommonServiceImpl commonService;

    @Value("${websiteUrl}")
    private String websiteUrl;




    @Override
    public CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) throws StripeException {
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

        try {

//        handle checkout or payment
            var builder =  SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel");

//       add the items to stripe now
            order.getItems().forEach(item -> {
                var lineItem =  SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .build()
                                        ).build()
                        ).build();
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());

            cartService.clearCart(cart.getId());
            return new CheckoutResponseDto(order.getId(), session.getUrl());

        }catch (StripeException e) {
            orderRepository.delete(order);
            throw e;
        }

    }
}
