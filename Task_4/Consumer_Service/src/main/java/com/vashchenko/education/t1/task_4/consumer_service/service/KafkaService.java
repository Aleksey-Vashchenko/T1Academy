package com.vashchenko.education.t1.task_4.consumer_service.service;

import com.vashchenko.education.t1.task_4.consumer_service.data.dto.MetricDto;
import com.vashchenko.education.t1.task_4.consumer_service.data.entity.MetricEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    @Value("${spring.kafka.topics.metric}")
    private static String metricTopic;
    private final MetricEventService service;
    @KafkaListener(id="metricGroup",topics = "metricTopic")
    public void lister(MetricDto metricEvent){
        System.out.println(metricEvent);
    }
}
