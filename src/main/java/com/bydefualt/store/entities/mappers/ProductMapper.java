package com.bydefualt.store.entities.mappers;

import com.bydefualt.store.dtos.ProductDto;
import com.bydefualt.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}