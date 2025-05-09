package com.bydefault.store.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "Product Id is required")
    @Positive(message = "Product Id must be a positive number")
    private Long productId;
}
