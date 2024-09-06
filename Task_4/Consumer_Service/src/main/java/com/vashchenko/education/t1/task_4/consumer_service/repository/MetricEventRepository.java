package com.vashchenko.education.t1.task_4.consumer_service.repository;

import com.vashchenko.education.t1.task_4.consumer_service.data.entity.MetricEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MetricEventRepository extends JpaRepository<MetricEvent, UUID> {
}
