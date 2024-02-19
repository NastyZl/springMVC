package org.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* org.example.controllers.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* org.example.controllers.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* org.example.controllers.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("After returning method: " + joinPoint.getSignature().getName() + ", Result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* org.example.controllers.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("After throwing method: " + joinPoint.getSignature().getName() + ", Exception: " + exception);
    }
}
