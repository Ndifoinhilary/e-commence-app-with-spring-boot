package com.bydefault.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate created_at;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE)
    private Set<CartItems> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return items.stream().map(CartItems::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
