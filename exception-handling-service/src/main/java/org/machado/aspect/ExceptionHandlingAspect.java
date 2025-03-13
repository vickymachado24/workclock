package org.machado.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
/**
 * Proceed with normal execution flows,
 * if you find any exceptions throw it
 * Global Exception Handler with Controller Advice will be used to handle the exceptions with the ResponseEntity
 */
@Slf4j
public class ExceptionHandlingAspect {

    @Around("@within(com.machado.person_service.annotation.HandleExceptions)")
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            throw ex;
        }
    }

}