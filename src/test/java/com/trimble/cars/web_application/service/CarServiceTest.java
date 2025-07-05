package com.trimble.cars.web_application.service;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.User;
import com.trimble.cars.web_application.repository.CarRepository;
import com.trimble.cars.web_application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    CarRepository carRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CarService carService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCar() {
        User owner = new User();
        owner.setUsername("owner1");
        owner.setRole("OWNER");

        when(userRepository.findByUsername("owner1")).thenReturn(owner);
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car car = carService.registerCar("owner1", "Tesla", "Model S");
        assertThat(car.getModel()).isEqualTo("Tesla");
        assertThat(car.getOwner().getUsername()).isEqualTo("owner1");
    }
}