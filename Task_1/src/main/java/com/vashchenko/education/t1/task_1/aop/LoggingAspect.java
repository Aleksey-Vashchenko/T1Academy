package com.vashchenko.education.t1.task_1.aop;

import com.vashchenko.education.t1.task_1.exception.BaseConflictException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.vashchenko.education.t1.task_1.aop.annotation.LogMethodExecution)")
    public void needLoggingMethod() {}


    @Around("needLoggingMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Method {}.{} called with parameters: {}", className, methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Method {}.{} returned with result: {}", className, methodName, result);
            return result;
        } catch (Exception ex) {
            if(isNeedToLogin(ex)){
                logger.error("Exception in method {}.{} with arguments {}. Exception: {}",
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
            logger.error("Exception in method {}.{} with arguments {}. Exception: {}",
                    className, methodName, args, ex.getMessage(), ex);
        }
    }

    private static boolean isNeedToLogin(Exception ex){
        return !(ex instanceof BaseConflictException);
    }
}
