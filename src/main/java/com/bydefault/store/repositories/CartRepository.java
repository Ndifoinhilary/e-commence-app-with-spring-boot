package com.bydefault.store.repositories;

import com.bydefault.store.entities.Carts;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Carts, UUID> {

    @EntityGraph(attributePaths = "items.product")
    @Query("select c from Carts c where c.id = :cartId")
    Optional<Carts> getCartWithItems(@Param("cartId") UUID cartId);
}
