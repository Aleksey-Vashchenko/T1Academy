package com.vashchenko.education.t1.task_1.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UuidGenerator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString(exclude = "orders")
@Data
@Table(name = "pr_users")
@DynamicUpdate
public class User {

    @UuidGenerator
    @Id
    @Column(updatable = false)
    UUID id;
    String name;
    String email;

    @OneToMany(mappedBy = "user", orphanRemoval = false, cascade = CascadeType.REMOVE)
    Set<Order> orders;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
