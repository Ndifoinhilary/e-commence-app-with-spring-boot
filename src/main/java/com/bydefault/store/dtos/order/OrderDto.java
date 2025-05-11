package com.bydefault.store.dtos.order;



import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime createdAt;
    private String status;
    private List<OrderItemsDto> items = new ArrayList<>();
    private BigDecimal totalPrice;

}
