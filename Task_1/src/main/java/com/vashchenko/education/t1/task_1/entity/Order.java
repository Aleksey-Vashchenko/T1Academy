package com.vashchenko.education.t1.task_1.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @UuidGenerator
    @Id
    UUID id;
    String description;
    String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return Objects.equals(id, order.id) && Objects.equals(description, order.description) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status);
    }
}
