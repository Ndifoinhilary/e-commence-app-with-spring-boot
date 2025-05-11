package com.bydefault.store.dtos.checkout;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequestDto {
    @NotNull(message = "The card Id is required")
    private UUID cartId;

}
