package com.bydefault.store.dtos.order;

import lombok.Data;

@Data
public class OrderProductDto {
    private Long id;
    private String name;
    private String price;
}
