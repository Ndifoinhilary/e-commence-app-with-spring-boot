package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.order.OrderDto;
import com.bydefault.store.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}
