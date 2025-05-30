package com.bydefault.store.repositories;

import com.bydefault.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Long category_id);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
    List<Product> findProductWithCategory();
}