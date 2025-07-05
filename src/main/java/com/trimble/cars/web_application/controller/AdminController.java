package com.trimble.cars.web_application.controller;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.model.User;
import com.trimble.cars.web_application.repository.UserRepository;
import com.trimble.cars.web_application.service.CarService;
import com.trimble.cars.web_application.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private CarService carService;
    @Autowired
    private LeaseService leaseService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-leases")
    public List<Lease> allLeases() {
        return leaseService.getAllLeases();
    }

    @GetMapping("/all-cars")
    public List<Car> allCars() {
        return carService.getAllCars();
    }

    @GetMapping("/all-users")
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}