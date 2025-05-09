package com.bydefault.store.repositories;

import com.bydefault.store.entities.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Carts, UUID> {
}
