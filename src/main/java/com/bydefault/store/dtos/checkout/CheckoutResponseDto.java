package com.bydefault.store.dtos.checkout;

import lombok.Data;

@Data
public class CheckoutResponseDto {
    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponseDto(Long orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
