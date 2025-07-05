package com.trimble.cars.web_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lease {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Car car;
    @ManyToOne
    private User customer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}