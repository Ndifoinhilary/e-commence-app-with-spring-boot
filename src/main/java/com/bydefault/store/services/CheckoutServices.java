package com.bydefault.store.services;

import com.bydefault.store.dtos.checkout.CheckoutRequestDto;
import com.bydefault.store.dtos.checkout.CheckoutResponseDto;

public interface CheckoutServices {
    CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto);
}
