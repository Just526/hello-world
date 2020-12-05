package com.dhcc.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.dhcc.demo.controller..*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void handle(JoinPoint joinPoint) {
        MDC.put("traceNo", "1111111111111");

        logger.info("joinPoint[{}],traceNo[{}]", joinPoint, MDC.get("traceNo"));
    }

}
