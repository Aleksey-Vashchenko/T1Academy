package com.vashchenko.education.t1.task_1.aop;

import com.vashchenko.education.t1.task_1.exception.BaseConflictException;
import com.vashchenko.education.t1.task_1.exception.BaseNotFoundException;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class LoggingAspectTest {

    @MockBean
    Logger logger;

    @BeforeEach
    void setUp() {
        LoggingAspect.log=logger;
    }

    @Test
    public void testLogMethodExecutionSuccessfulMethod() throws Throwable {
        LoggingAspect loggingAspect = new LoggingAspect();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        loggingAspect.logAround(joinPoint);
        verify(logger, times(2)).info(anyString(),any(),any(),any());
    }

    @Test
    public void testLogMethodExecutionLoggingExceptionMethod() throws Throwable {
        LoggingAspect loggingAspect = new LoggingAspect();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.proceed()).thenThrow(BaseNotFoundException.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        try {
            loggingAspect.logAround(joinPoint);
        }
        catch (Exception ignored){

        }
        verify(logger, times(1)).info(anyString(),any(),any(),any());
        verify(logger, times(1)).error(anyString(),any(),any(),any(),any());
    }
    @Test
    public void testLogMethodExecutionUnloggingExceptionMethod() throws Throwable {
        LoggingAspect loggingAspect = new LoggingAspect();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.proceed()).thenThrow(BaseConflictException.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        try {
            loggingAspect.logAround(joinPoint);
        }
        catch (Exception ignored){

        }
        verify(logger, times(1)).info(anyString(),any(),any(),any());
        verify(logger, times(0)).error(anyString(),any(),any(),any(),any());
    }

    @Test
    public void testLogExceptionOnlyLoggingExceptionMethod() throws Throwable {
        LoggingAspect loggingAspect = new LoggingAspect();
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        try {
            loggingAspect.logAfterThrowing(joinPoint,new BaseNotFoundException("test"));
        }
        catch (Exception ignored){

        }
        verify(logger, times(1)).error(anyString(),any(),any(),any(),any());
    }

    @Test
    public void testLogExceptionOnlyUnloggingExceptionMethod() throws Throwable {
        LoggingAspect loggingAspect = new LoggingAspect();
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        try {
            loggingAspect.logAfterThrowing(joinPoint,new BaseConflictException("test"));
        }
        catch (Exception ignored){

        }
        verify(logger, times(0)).error(anyString(),any(),any(),any(),any());
    }

}