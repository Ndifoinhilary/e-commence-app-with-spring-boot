package com.bydefault.store.services;

import com.bydefault.store.entities.Order;

public interface PaymentGateServices {
    CheckoutSession createCheckoutSession(Order order);
}
