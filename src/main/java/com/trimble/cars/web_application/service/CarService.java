package com.trimble.cars.web_application.service;

import com.trimble.cars.web_application.exceptions.ResourceNotFoundException;
import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.User;
import com.trimble.cars.web_application.repository.CarRepository;
import com.trimble.cars.web_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public Car registerCar(String username, String model, String variant) {
        User owner = userRepository.findByUsername(username);
        if (owner == null) throw new RuntimeException("Owner not found");
        Car car = new Car();
        car.setModel(model);
        car.setVariant(variant);
        car.setStatus("IDLE");
        car.setOwner(owner);
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        List<Car> carList = carRepository.findAll();
        if(carList==null || carList.size()<0){
            throw new ResourceNotFoundException("Resource Not available");
        }
        return carList;
    }

    public List<Car> getCarsByOwner(String username) {
        User owner = userRepository.findByUsername(username);
        if (owner == null) throw new RuntimeException("Owner not found");
        return carRepository.findByOwner(owner);
    }
}