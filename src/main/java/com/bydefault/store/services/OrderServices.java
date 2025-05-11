package com.bydefault.store.services;

import com.bydefault.store.dtos.order.OrderDto;

import java.util.List;

public interface OrderServices {
    List<OrderDto> getAllOrders();
}
