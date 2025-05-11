package com.bydefault.store.controller;



import com.bydefault.store.dtos.order.OrderDto;
import com.bydefault.store.services.OrderServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/")
@AllArgsConstructor
public class OrderController {
    private OrderServices orderServices;


    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderServices.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("{id}/")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderServices.getOrderById(id), HttpStatus.OK);
    }
}
