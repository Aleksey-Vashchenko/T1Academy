package com.vashchenko.education.t1.task_1.aop;

import com.vashchenko.education.t1.task_1.exception.BaseConflictException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {
    @Pointcut("@annotation(com.vashchenko.education.t1.task_1.aop.annotation.LogMethodExecution)")
    public void needLoggingMethod() {}


    @Around("needLoggingMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Method {}.{} called with parameters: {}", className, methodName, args);

        try {
            Object result = joinPoint.proceed();
            log.info("Method {}.{} returned with result: {}", className, methodName, result);
            return result;
        } catch (Exception ex) {
            if(isNeedToLogin(ex)){
                log.error("Exception in method {}.{} with arguments {}. Exception: {}",
                        className, methodName, args, ex.getMessage(), ex);
            }
            throw ex;
        }
    }

    @Pointcut("@annotation(com.vashchenko.education.t1.task_1.aop.annotation.LogExceptionOnly)")
    public void needExceptionLogging() {}

    @AfterThrowing(pointcut = "needExceptionLogging()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        if(isNeedToLogin(ex)){
            log.error("Exception in method {}.{} with arguments {}. Exception: {}",
                    className, methodName, args, ex.getMessage(), ex);
        }
    }

    private static boolean isNeedToLogin(Exception ex){
        return !(ex instanceof BaseConflictException);
    }
}
