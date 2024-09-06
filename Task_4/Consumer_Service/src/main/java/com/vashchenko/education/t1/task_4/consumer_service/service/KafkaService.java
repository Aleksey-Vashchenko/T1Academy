package com.vashchenko.education.t1.task_4.consumer_service.service;

import com.vashchenko.education.t1.task_4.consumer_service.data.dto.MetricDto;
import com.vashchenko.education.t1.task_4.consumer_service.data.entity.MetricEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaService {
    private final MetricEventService service;
    @KafkaListener(id="metricGroup",topics = "metricTopic")
    public void listenMetric(MetricDto metricDto){
        service.save(convertToEntity(metricDto));
    }

    @KafkaListener(id="deadMetricGroup",topics = "deadMetricTopic")
    public void listenDeadMetric(MetricDto metricDto){
        log.warn("Was find dead metric : " + metricDto.toString());
    }

    private MetricEvent convertToEntity(MetricDto metricDto){
        MetricEvent metricEvent = new MetricEvent();
        metricEvent.setSender(metricDto.getFrom());
        metricEvent.setMessage(metricDto.getMessage());
        return metricEvent;
    }
}
