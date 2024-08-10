package com.vashchenko.education.t1.task_1;

import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;
import com.vashchenko.education.t1.task_1.model.entity.OrderStatus;
import com.vashchenko.education.t1.task_1.service.OrderService;
import com.vashchenko.education.t1.task_1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class Task1Application {

    private final UserService userService;
    private final OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(Task1Application.class, args);
    }

    @Profile("!test")
    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady(){
        UserDto userToSave = new UserDto(null,"leha.vashchenko@gmail.com","Lesha");
        UserDto savedUser = userService.createUser(userToSave);
        System.out.println("Saved user with id: "+savedUser.id());
        OrderDto orderDto = new OrderDto(null,"new order",OrderStatus.New,savedUser.id());
        OrderDto savedOrder = orderService.createOrder(orderDto);
        System.out.println("Saved order with id: "+savedOrder.id());


    }

}
