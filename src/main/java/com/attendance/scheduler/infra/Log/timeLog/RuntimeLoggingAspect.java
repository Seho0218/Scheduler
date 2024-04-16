package com.attendance.scheduler.infra.Log.timeLog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RuntimeLoggingAspect {

    @Around("execution(* com.attendance.scheduler.*.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        log.info("At Controller " + methodName + " takes " + runtime + "ms");
        return result;
    }

    @Around("execution(* com.attendance.scheduler.*.application.*.*(..))")
    public Object logApplication(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        log.info("At Application " + methodName + " takes " + runtime + "ms");
        return result;
    }

    @Around("execution(* com.attendance.scheduler.*.repository.*.*(..))")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        log.info("At Repository " + methodName + " takes " + runtime + "ms");
        return result;
    }
}
