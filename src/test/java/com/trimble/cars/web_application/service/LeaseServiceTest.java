package com.trimble.cars.web_application.service;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.model.User;
import com.trimble.cars.web_application.repository.CarRepository;
import com.trimble.cars.web_application.repository.LeaseRepository;
import com.trimble.cars.web_application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class LeaseServiceTest {

    @Mock
    LeaseRepository leaseRepository;
    @Mock
    CarRepository carRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    LeaseService leaseService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartLeaseSuccess() {
        User customer = new User();
        customer.setUsername("cust1");
        customer.setRole("CUSTOMER");

        Car car = new Car();
        car.setId(1L);
        car.setStatus("IDLE");

        when(userRepository.findByUsername("cust1")).thenReturn(customer);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(leaseRepository.findByStatusAndCustomer("ACTIVE", customer)).thenReturn(Collections.emptyList());
        when(leaseRepository.save(any(Lease.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Lease lease = leaseService.startLease(1L, "cust1");
        assertThat(lease.getCar().getStatus()).isEqualTo("ON_LEASE");
        assertThat(lease.getCustomer().getUsername()).isEqualTo("cust1");
        assertThat(lease.getStatus()).isEqualTo("ACTIVE");
    }
}