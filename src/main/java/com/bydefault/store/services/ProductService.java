package com.bydefault.store.services;

import com.bydefault.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll(Long categoryId);
    ProductDto findById(Long id);
    ProductDto create(ProductDto product);
    ProductDto update(ProductDto product, Long id);
    void deleteById(Long id);
}
