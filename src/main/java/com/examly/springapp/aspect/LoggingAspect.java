package com.examly.springapp.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(*com.examly.springapp.service.*.*(..))")
    public void logBefore(){
        logger.info("Method execution started");
    }

    @AfterReturning("execution(*com.examly.springapp.service.*.*(..))")
    public void logAfterReturning(){
        logger.info("Method execution completed successfully");
    }
}
