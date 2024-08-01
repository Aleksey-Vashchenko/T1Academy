package com.vashchenko.education.t1.task_1.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation for logging only in case of exceptions.
 * Apply this annotation to methods where you want to log only exceptional situations.
 * Do not use this annotation together with {@link LogMethodExecution}, as the latter includes the functionality of this annotation.
 * @author Aliaksey Vashchenko
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExceptionOnly {
}
