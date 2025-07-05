package com.trimble.cars.web_application.service;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.model.User;
import com.trimble.cars.web_application.repository.CarRepository;
import com.trimble.cars.web_application.repository.LeaseRepository;
import com.trimble.cars.web_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaseService {
    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public Lease startLease(Long carId, String customerUsername) {
        User customer = userRepository.findByUsername(customerUsername);
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        if (!car.getStatus().equals("IDLE")) throw new RuntimeException("Car not available");
        List<Lease> active = leaseRepository.findByStatusAndCustomer("ACTIVE", customer);
        if (active.size() >= 2) throw new RuntimeException("Max 2 leases allowed");
        car.setStatus("ON_LEASE");
        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now());
        lease.setStatus("ACTIVE");
        leaseRepository.save(lease);
        carRepository.save(car);
        return lease;
    }

    public Lease endLease(Long leaseId, String username) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));
        if (!lease.getStatus().equals("ACTIVE")) throw new RuntimeException("Lease is not active");
        if (!lease.getCustomer().getUsername().equals(username)) throw new RuntimeException("Not authorized");
        lease.setEndDate(LocalDateTime.now());
        lease.setStatus("ENDED");
        Car car = lease.getCar();
        car.setStatus("IDLE");
        carRepository.save(car);
        leaseRepository.save(lease);
        return lease;
    }

    public List<Lease> getLeaseHistoryForCustomer(String username) {
        User customer = userRepository.findByUsername(username);
        return leaseRepository.findByCustomer(customer);
    }

    public List<Lease> getLeaseHistoryForCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow();
        return leaseRepository.findByCar(car);
    }

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }
}