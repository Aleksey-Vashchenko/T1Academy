package com.vashchenko.education.t1.task_3.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class RefreshToken {
    @Id
    @Column(nullable = false,updatable = false)
    UUID userId;
    String tokenValue;
    Date createdAt;
    Date expiredAt;
    Integer accessHash;
}
