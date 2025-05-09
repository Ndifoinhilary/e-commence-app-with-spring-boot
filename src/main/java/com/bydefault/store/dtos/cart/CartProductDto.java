package com.bydefault.store.dtos.cart;

import lombok.Data;

@Data
public class CartProductDto {
    private Long id;
    private String name;
    private String price;
}
