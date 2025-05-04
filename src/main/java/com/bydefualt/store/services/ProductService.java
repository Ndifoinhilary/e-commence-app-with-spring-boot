package com.bydefualt.store.services;

import com.bydefualt.store.dtos.ProductDto;
import com.bydefualt.store.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll(Byte categoryId);
    ProductDto findById(Long id);
    ProductDto save(ProductDto product);
    ProductDto update(ProductDto product, Long id);
    void deleteById(Long id);
}
