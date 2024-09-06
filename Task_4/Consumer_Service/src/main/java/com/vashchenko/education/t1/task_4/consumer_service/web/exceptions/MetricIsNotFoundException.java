package com.vashchenko.education.t1.task_4.consumer_service.web.exceptions;

import java.util.UUID;

public class MetricIsNotFoundException extends RuntimeException{
    public MetricIsNotFoundException(UUID uuid) {
        super("Metric with id "+uuid+" not found");
    }
}
