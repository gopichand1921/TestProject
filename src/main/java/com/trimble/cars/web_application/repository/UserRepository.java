package com.trimble.cars.web_application.repository;

import com.trimble.cars.web_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}