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
    private final MetricEventService service;
    @KafkaListener(id="metricGroup",topics = "metricTopic")
    public void lister(MetricDto metricDto){
        service.save(convertToEntity(metricDto));
    }

    private MetricEvent convertToEntity(MetricDto metricDto){
        MetricEvent metricEvent = new MetricEvent();
        metricEvent.setSender(metricDto.getFrom());
        metricEvent.setMessage(metricDto.getMessage());
        return metricEvent;
    }
}
