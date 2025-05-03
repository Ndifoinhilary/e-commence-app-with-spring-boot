package com.bydefualt.store.repositories;

import com.bydefualt.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}