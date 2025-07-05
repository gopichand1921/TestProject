package com.trimble.cars.web_application.repository;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomer(User customer);

    List<Lease> findByCar(Car car);

    List<Lease> findByStatusAndCustomer(String status, User customer);
}