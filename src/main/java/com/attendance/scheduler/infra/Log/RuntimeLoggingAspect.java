package com.attendance.scheduler.infra.Log;

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
        String layer = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        try {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            log.info(layer+ " " + methodName + " takes " + runtime + "ms");
            return result;
        }catch(Throwable e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    @Around("execution(* com.attendance.scheduler.*.application.*.*(..))")
    public Object logApplication(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        log.info(layer+ " " + methodName + " takes " + runtime + "ms");

        return result;
    }

    @Around("execution(* com.attendance.scheduler.*.repository.*.*(..))")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        log.info(layer+ " " + methodName + " takes " + runtime + "ms");

        return result;
    }
}
