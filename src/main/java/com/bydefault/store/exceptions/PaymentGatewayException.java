package com.bydefault.store.exceptions;

public class PaymentGatewayException extends RuntimeException {
    private String message;

    public PaymentGatewayException(String message) {
        super(message);
    }
}
