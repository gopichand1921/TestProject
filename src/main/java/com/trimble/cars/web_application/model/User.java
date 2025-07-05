package com.trimble.cars.web_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role; // ADMIN, OWNER, CUSTOMER
}