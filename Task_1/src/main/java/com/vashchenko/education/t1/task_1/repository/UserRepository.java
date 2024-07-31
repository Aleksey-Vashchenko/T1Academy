package com.vashchenko.education.t1.task_1.repository;

import com.vashchenko.education.t1.task_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
