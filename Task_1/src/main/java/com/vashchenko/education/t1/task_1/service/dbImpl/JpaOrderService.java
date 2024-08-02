package com.vashchenko.education.t1.task_1.service.dbImpl;

import com.vashchenko.education.t1.task_1.aop.annotation.LogExceptionOnly;
import com.vashchenko.education.t1.task_1.aop.annotation.LogMethodExecution;
import com.vashchenko.education.t1.task_1.exception.OrderIsNotFoundException;
import com.vashchenko.education.t1.task_1.exception.UserIsNotFoundException;
import com.vashchenko.education.t1.task_1.mapper.OrderMapper;
import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.model.entity.Order;
import com.vashchenko.education.t1.task_1.model.entity.User;
import com.vashchenko.education.t1.task_1.repository.OrderRepository;
import com.vashchenko.education.t1.task_1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JpaUserService userService;

    @Override
    @LogMethodExecution
    public OrderDto createOrder(OrderDto dtoToCreate) {
        UUID userId = dtoToCreate.userId();
        if(userService.ifUserExistsById(userId)){
            Order orderToCreate =orderMapper.toEntity(dtoToCreate);
            User userOfOrder = new User();
            userOfOrder.setId(userId);
            orderToCreate.setUser(userOfOrder);
            return orderMapper.toDto(
                    orderRepository.save(orderToCreate)
            );
        }
        else {
            throw new UserIsNotFoundException("id",userId);
        }
    }

    @Override
    @LogMethodExecution
    public void updateOrder(OrderDto dtoToUpdate, UUID orderId) {
        if(!orderRepository.existsById(orderId)){
            Order updateOrder = orderMapper.toEntity(dtoToUpdate);
            updateOrder.setId(orderId);
            orderRepository.save(updateOrder);
        }
        else {
            throw new OrderIsNotFoundException("id",orderId);
        }
    }

    @Override
    @LogExceptionOnly
    public void deleteOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderIsNotFoundException("id",orderId)
        );

        orderRepository.delete(order);
    }

    @Override
    @LogExceptionOnly
    public OrderDto findOrderById(UUID orderId) {
        return orderMapper.toDto(
                orderRepository.findById(orderId).orElseThrow(
                        () -> new OrderIsNotFoundException("id",orderId)
                )
        );
    }

    @Override
    @LogExceptionOnly
    public List<OrderDto> findAllOrdersByUserId(UUID userId) {
        if(userService.ifUserExistsById(userId)){
            return orderMapper.toDtoList(
                    orderRepository.findByUser_Id(userId)
            );
        }
        else {
            throw new UserIsNotFoundException("id",userId);
        }
    }
}
