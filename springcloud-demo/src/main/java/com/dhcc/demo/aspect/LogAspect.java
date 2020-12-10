package com.dhcc.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.dhcc.demo.controller..*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object handle(ProceedingJoinPoint joinPoint) {
//      MDC.put("traceNo", "1111111111111");
//      logger.info("joinPoint[{}],traceNo[{}]", joinPoint, MDC.get("traceNo"));
         Object response = null;
        long start=System.currentTimeMillis();
        try {
             response = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.info("请求处理耗时："+(System.currentTimeMillis()-start));
        return response;
    }

}
