package com.bydefault.store.controller;


import com.bydefault.store.dtos.ProductDto;
import com.bydefault.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false, defaultValue = "", name = "categoryId") Long categoryId) {

        return ResponseEntity.ok(productService.findAll(categoryId));

    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
}
