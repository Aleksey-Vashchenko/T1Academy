package com.vashchenko.education.t1.task_1.model.dto.request;

import com.vashchenko.education.t1.task_1.model.entity.OrderStatus;

import java.util.UUID;

public record OrderDto (UUID id, String description, OrderStatus status){
}
