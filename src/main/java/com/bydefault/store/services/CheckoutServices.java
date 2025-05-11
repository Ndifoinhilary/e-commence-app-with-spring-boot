package com.bydefault.store.services;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;
import com.stripe.exception.StripeException;

public interface CheckoutServices {
    CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) throws StripeException;
}
