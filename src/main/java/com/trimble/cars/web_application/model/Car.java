package com.trimble.cars.web_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "car_details")
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private String variant;
    private String status;
    @ManyToOne
    private User owner;
}