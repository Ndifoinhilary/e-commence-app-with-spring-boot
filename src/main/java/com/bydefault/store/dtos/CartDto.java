package com.bydefault.store.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private List<CartItemDto> items = new ArrayList<>();

    private BigDecimal totalPrice = BigDecimal.ZERO;
}
