package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.ProductDto;
import com.bydefault.store.entities.Product;
import com.bydefault.store.entities.mappers.ProductMapper;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.ProductRepository;
import com.bydefault.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServicesImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll(Long categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {

            products = productRepository.findProductWithCategory();
        }
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No product found with id " + id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto save(ProductDto product) {
        return null;
    }

    @Override
    public ProductDto update(ProductDto product, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
