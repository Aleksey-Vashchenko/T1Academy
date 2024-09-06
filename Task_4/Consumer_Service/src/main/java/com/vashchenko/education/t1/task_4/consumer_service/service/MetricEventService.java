package com.vashchenko.education.t1.task_4.consumer_service.service;

import com.vashchenko.education.t1.task_4.consumer_service.data.entity.MetricEvent;
import com.vashchenko.education.t1.task_4.consumer_service.repository.MetricEventRepository;
import com.vashchenko.education.t1.task_4.consumer_service.web.exceptions.MetricIsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetricEventService {
    private final MetricEventRepository repository;

    public MetricEvent getMetricEvent(UUID id){
        return repository.findById(id).orElseThrow(() -> new MetricIsNotFoundException(id));
    }

    public void save(MetricEvent metricEvent) {
        repository.save(metricEvent);
    }
}
