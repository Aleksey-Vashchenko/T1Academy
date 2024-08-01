package com.vashchenko.education.t1.task_1.web.controllers;

import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Object createOrder(@PathVariable UUID userId,
                        @RequestBody OrderDto orderDto){
        return orderService.createOrder(orderDto,userId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Object getUserOrders(@PathVariable UUID userId){
        return orderService.findAllOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    Object getOrder(@PathVariable UUID orderId){
        return orderService.findOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Object deleteOrder(@PathVariable UUID orderId){
        orderService.deleteOrderById(orderId);
        return null;
    }
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Object updateOrder(@PathVariable UUID orderId,
                    @RequestBody OrderDto orderDto){
        orderService.updateOrder(orderDto,orderId);
        return null;
    }
}
