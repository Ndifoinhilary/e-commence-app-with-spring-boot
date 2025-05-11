package com.bydefault.store.dtos.checkout;

import lombok.Data;

@Data
public class CheckoutResponseDto {
    private Long orderId;

    public CheckoutResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}
