package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.cart.CartDto;
import com.bydefault.store.dtos.cart.CartItemDto;
import com.bydefault.store.entities.CartItems;
import com.bydefault.store.entities.Carts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    CartDto toDto(Carts cart);

    @Mapping(target = "totalPrice", expression = "java(cartItems.getTotalPrice())")
    CartItemDto toDto(CartItems cartItems);
}
