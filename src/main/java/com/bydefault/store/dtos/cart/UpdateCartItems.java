package com.bydefault.store.dtos.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateCartItems {

    @NotNull(message = "This field is required for update")
    @Positive(message = "Quantity entered must be positive")
    private Integer quantity;
}
