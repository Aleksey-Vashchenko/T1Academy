package com.vashchenko.education.t1.task_1.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Annotation for validation {@link com.vashchenko.education.t1.task_1.model.entity.OrderStatus}.
 * Apply this annotation to fields in classes to validate OrderStatus in {@link org.springframework.web.bind.annotation.RestController} classes
 * @author Aliaksey Vashchenko
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface OrderType {
}
