package com.vashchenko.education.t1.task_1.mapper;

import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.model.entity.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface OrderMapper extends BaseMapper<Order, OrderDto> {
    @Override
    Order toEntity(OrderDto to);

    @Override
    OrderDto toDto(Order entity);

    @Override
    List<OrderDto> toDtoList(Collection<Order> entities);
}
