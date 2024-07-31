package com.vashchenko.education.t1.task_1.repository;

import com.vashchenko.education.t1.task_1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
