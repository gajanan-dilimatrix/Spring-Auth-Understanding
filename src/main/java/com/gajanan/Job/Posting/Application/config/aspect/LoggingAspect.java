package com.gajanan.Job.Posting.Application.config.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    private final ObjectMapper objectMapper;

    @Pointcut(value = "within(com.gajanan.Job.Posting.Application.controller..*) || within(com.gajanan.Job.Posting.Application.service.impl..*)")
    public void pointcut() {
    }

    @Before(value = "pointcut()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        LOGGER.info("Entered: {}.{} - args: {} ", className, methodName, objectToJSON(Arrays.asList(args)));
    }

    @AfterReturning(value = "pointcut()", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {

        if (isLoggingDisabled(joinPoint)) return;

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        LOGGER.info("Successfully: {}.{} - response: {} ", className, methodName, objectToJSON(response));
    }

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        LOGGER.error("Failure: {}.{} - exception: {} ", className, methodName, ex.getMessage());
    }


    private String objectToJSON(Object object) {
        try {
            return object != null ? objectMapper.writeValueAsString(object) : null;
        } catch (Exception ex) {
            LOGGER.warn("Error converting object to JSON", ex);
        }
        return object.toString();
    }

    private boolean isLoggingDisabled(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().isAnnotationPresent(NoLogging.class);
    }


}
