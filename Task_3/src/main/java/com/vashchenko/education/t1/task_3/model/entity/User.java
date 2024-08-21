package com.vashchenko.education.t1.task_3.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class User {

    @Id
    @UuidGenerator
    @Column(updatable = false)
    UUID id;

    @Column(name = "email", nullable = false)
    String email;
    @Column(nullable = false)
    String username;
    @Column(name = "password",nullable = false)
    String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
