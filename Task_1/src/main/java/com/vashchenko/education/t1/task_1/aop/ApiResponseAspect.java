package com.vashchenko.education.t1.task_1.aop;

import com.vashchenko.education.t1.task_1.model.dto.response.ApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.lang.reflect.Method;

@Aspect
@Component
public class ApiResponseAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    //TODO: узнать почему не работает @AfterReturning
    public Object wrapResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            if (method.isAnnotationPresent(ResponseStatus.class)) {
                ResponseStatus annotation = method.getAnnotation(ResponseStatus.class);
                httpStatus = annotation.value();
            }
        }
        catch (Exception ignored) {}
        return new ApiResponse<>(httpStatus, result);
    }
}
