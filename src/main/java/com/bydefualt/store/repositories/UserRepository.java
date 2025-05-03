package com.bydefualt.store.repositories;

import com.bydefualt.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
