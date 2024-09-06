package com.vashchenko.education.t1.task_4.producer_service.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MetricDto {
    String from;
    String message;
}
