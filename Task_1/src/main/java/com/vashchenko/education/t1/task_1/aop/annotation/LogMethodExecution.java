package com.vashchenko.education.t1.task_1.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation for logging only in case of exceptions.
 * Apply this annotation to methods where you want to log only exceptional situations.
 * Includes the functionality of {@link LogExceptionOnly} by logging both successful executions and exceptions.
 * Do not use this annotation together with {@link LogExceptionOnly}, as this annotation encompasses its functionality.
 * @author Aliaksey Vashchenko
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethodExecution {
}
