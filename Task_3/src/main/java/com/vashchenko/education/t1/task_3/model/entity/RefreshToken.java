package com.vashchenko.education.t1.task_3.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class RefreshToken {
    @Id
    @UuidGenerator
    UUID uuid;
    String tokenValue;
    Date createdAt;
    Date expiredAt;
    UUID userId;
}
