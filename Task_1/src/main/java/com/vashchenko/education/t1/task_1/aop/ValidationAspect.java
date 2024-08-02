package com.vashchenko.education.t1.task_1.aop;

import com.vashchenko.education.t1.task_1.aop.annotation.OrderType;
import com.vashchenko.education.t1.task_1.exception.BaseBadRequestException;
import com.vashchenko.education.t1.task_1.model.dto.request.OrderDto;
import com.vashchenko.education.t1.task_1.model.entity.OrderStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect
@Component
public class ValidationAspect {

    @Pointcut("execution(* *(.., com.vashchenko.education.t1.task_1.model.dto.request.OrderDto, ..)) && within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethodsWithOrderDto() {}

    @Before("restControllerMethodsWithOrderDto()")
    public void checkOrderType(JoinPoint joinPoint) throws IllegalAccessException {
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null && arg.getClass().equals(OrderDto.class)) {
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(OrderType.class)) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(arg);
                        try {
                            OrderStatus.valueOf(fieldValue.toString());
                        } catch (Exception e) {
                            throw new BaseBadRequestException(
                                        "OrderType","condition","Must be one of: " + Arrays.toString(OrderStatus.values())
                            );
                        }
                    }
                }
            }
        }
    }
}
