package com.vashchenko.education.t1.task_3.repository;

import com.vashchenko.education.t1.task_3.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}
