package com.in28minutes.rest.webservices.restfulwebservices.Beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "user_det")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, message = "too short name")
    private String name;

    @Past(message = "Your date is in the future")
    private LocalDate birthdate;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts ;





}
