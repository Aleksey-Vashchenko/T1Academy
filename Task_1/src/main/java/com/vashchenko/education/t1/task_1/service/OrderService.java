package com.vashchenko.education.t1.task_1.service;

import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto createOrder(OrderDto dtoToCreate, UUID userId);
    void updateOrder(OrderDto dtoToUpdate, UUID id);
    void deleteOrderById(UUID orderId);
    OrderDto findOrderById(UUID orderId);
    List<OrderDto> findAllOrdersByUserId(UUID serId);
}
