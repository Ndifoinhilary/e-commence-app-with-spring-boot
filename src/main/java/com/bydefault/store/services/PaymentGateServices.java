package com.bydefault.store.services;

import com.bydefault.store.dtos.checkout.PaymentResult;
import com.bydefault.store.dtos.checkout.WebhookRequest;
import com.bydefault.store.entities.Order;

import java.util.Optional;

public interface PaymentGateServices {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);;
}
