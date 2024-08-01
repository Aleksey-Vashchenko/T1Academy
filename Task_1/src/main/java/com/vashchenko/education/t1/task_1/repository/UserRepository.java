package com.vashchenko.education.t1.task_1.repository;

import com.vashchenko.education.t1.task_1.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);

    @Override
    boolean existsById(UUID uuid);

    Optional<User> findByEmailIgnoreCase(String email);


}
