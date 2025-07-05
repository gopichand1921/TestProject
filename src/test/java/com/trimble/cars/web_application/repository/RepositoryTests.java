package com.trimble.cars.web_application.repository;

import com.trimble.cars.web_application.model.Car;
import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    LeaseRepository leaseRepository;

    @Test
    public void testUserRepository() {
        User user = new User();
        user.setUsername("owner1");
        user.setPassword("pw");
        user.setRole("OWNER");
        userRepository.save(user);

        User loaded = userRepository.findByUsername("owner1");
        assertThat(loaded).isNotNull();
        assertThat(loaded.getRole()).isEqualTo("OWNER");
    }

    @Test
    public void testCarRepository() {
        User owner = new User();
        owner.setUsername("owner2");
        owner.setPassword("pw");
        owner.setRole("OWNER");
        userRepository.save(owner);

        Car car = new Car();
        car.setModel("Honda");
        car.setVariant("Civic");
        car.setStatus("IDLE");
        car.setOwner(owner);
        carRepository.save(car);

        List<Car> cars = carRepository.findByOwner(owner);
        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getModel()).isEqualTo("Honda");
    }

    @Test
    public void testLeaseRepository() {
        User customer = new User();
        customer.setUsername("cust1");
        customer.setPassword("pw");
        customer.setRole("CUSTOMER");
        userRepository.save(customer);

        User owner = new User();
        owner.setUsername("owner3");
        owner.setPassword("pw");
        owner.setRole("OWNER");
        userRepository.save(owner);

        Car car = new Car();
        car.setModel("Toyota");
        car.setVariant("Corolla");
        car.setStatus("IDLE");
        car.setOwner(owner);
        carRepository.save(car);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStatus("ACTIVE");
        lease.setStartDate(LocalDateTime.now());
        leaseRepository.save(lease);

        List<Lease> leases = leaseRepository.findByCustomer(customer);
        assertThat(leases).hasSize(1);
        assertThat(leases.get(0).getCar().getModel()).isEqualTo("Toyota");
    }
}