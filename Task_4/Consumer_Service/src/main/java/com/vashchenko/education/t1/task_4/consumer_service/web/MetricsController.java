package com.vashchenko.education.t1.task_4.consumer_service.web;

import com.vashchenko.education.t1.task_4.consumer_service.data.entity.MetricEvent;
import com.vashchenko.education.t1.task_4.consumer_service.service.MetricEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MetricsController {
    private final MetricEventService service;
    @GetMapping("metrics/{id}")
    public MetricEvent getMetricById(@PathVariable("id")UUID id){
        return service.getMetricEvent(id);
    }

    @GetMapping("metrics/count")
    public ResponseEntity<Long> getCount(){
        return new ResponseEntity<>(service.getMetricsCount(), HttpStatus.OK);
    }
    @GetMapping("metrics/count/{service}")
    public ResponseEntity<Long> getCount(@PathVariable("service") String serviceName){
        return new ResponseEntity<>(service.getMetricsCountByService(serviceName), HttpStatus.OK);
    }

}
