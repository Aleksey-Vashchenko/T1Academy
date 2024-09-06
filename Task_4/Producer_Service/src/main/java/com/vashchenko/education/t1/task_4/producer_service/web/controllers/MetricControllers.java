package com.vashchenko.education.t1.task_4.producer_service.web.controllers;

import com.vashchenko.education.t1.task_4.producer_service.web.dto.MetricDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequiredArgsConstructor
public class MetricControllers {

    private final KafkaTemplate<Object,Object> kafkaTemplate;

    @PostMapping("/metrics")
    public void addMetric(@RequestBody MetricDto dto){
        kafkaTemplate.send("metricTopic",dto);
    }
}
