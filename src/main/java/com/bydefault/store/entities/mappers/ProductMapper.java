package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.ProductDto;
import com.bydefault.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    // Mapping FROM Product (entity) TO ProductDto
    @Mapping(target = "categoryId", expression = "java(product.getCategory() != null ? product.getCategory().getId() : null)")
    ProductDto toDto(Product product);

    // Mapping FROM ProductDto TO Product (entity)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto productDto);

    // Partial update (used in update method)
    @Mapping(target = "category", ignore = true)
    void updateEntity(ProductDto productDto, @MappingTarget Product product);

}