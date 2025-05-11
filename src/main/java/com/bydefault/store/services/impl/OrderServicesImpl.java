package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.order.OrderDto;
import com.bydefault.store.entities.mappers.OrderMapper;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.exceptions.UserNotAuthorizedException;
import com.bydefault.store.repositories.OrderRepository;
import com.bydefault.store.services.OrderServices;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderServicesImpl implements OrderServices {
    private OrderRepository orderRepository;
    private CommonServiceImpl commonService;
    private OrderMapper orderMapper;

    @Override
    public List<OrderDto> getAllOrders() {
        var user = commonService.getCurrentUser();
        var orders = orderRepository.getAllOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        var user = commonService.getCurrentUser();
        var order = orderRepository.getOrderWithItems(id).orElseThrow(() -> new ResourceNotFoundException("No order found with id: " + id));
        if (!order.getCustomer().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("You are not allow to access this order");
        }

        return orderMapper.toOrderDto(order);
    }
}
