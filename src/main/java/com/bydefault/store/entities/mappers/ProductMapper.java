package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.ProductDto;
import com.bydefault.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "categoryId", expression = "java(product.getCategory() != null ? product.getCategory().getId() : null)")
    ProductDto toDto(Product product);

}