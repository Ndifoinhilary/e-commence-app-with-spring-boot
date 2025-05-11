package com.bydefault.store.services;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.bydefault.store.dtos.checkout.WebhookRequest;
import com.stripe.exception.StripeException;

import java.util.Map;

public interface CheckoutServices {
    CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) throws StripeException;

    void handleWebhookEvent(WebhookRequest request);
}
