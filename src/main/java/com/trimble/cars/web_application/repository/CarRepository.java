package com.trimble.cars.web_application.repository;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwner(User owner);
}