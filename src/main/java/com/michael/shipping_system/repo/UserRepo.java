package com.michael.shipping_system.repo;

import com.michael.shipping_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
