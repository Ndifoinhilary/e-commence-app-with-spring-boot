package com.bydefault.store.services.impl;

import com.bydefault.store.entities.User;
import com.bydefault.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommonServiceImpl {

    private UserRepository userRepository;

    public User getCurrentUser() {
        var userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userId).orElse(null);
    }
}
