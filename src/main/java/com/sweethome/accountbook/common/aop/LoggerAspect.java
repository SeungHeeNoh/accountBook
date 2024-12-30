package com.sweethome.accountbook.common.aop;

import com.sweethome.accountbook.common.exception.SystemException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class.getSimpleName());

    @Pointcut("execution(* com.sweethome.accountbook.controller..*(..))")
    private void controllerPointcut() {}

    @Pointcut("execution(* com.sweethome.accountbook.service..*(..))")
    private void servicePointcut() {}

    @Pointcut("execution(* com.sweethome.accountbook.mapper..*(..))")
    private void mapperPointcut() {}


    @Around("controllerPointcut() || servicePointcut() || mapperPointcut() ")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        try {
            logger.info("[{}.{}] start with parameters: {}", className, methodName, Arrays.toString(args));
            Object result = joinPoint.proceed();
            String resultString = result == null ? null : result.toString();
            logger.info("[{}.{}] end with result: {}", className, methodName, resultString);
            return result;
        } catch (SystemException e) {
            logger.error("[{}.{}] end with exception: {}", className, methodName, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("[{}.{}] end with exception: {}", className, methodName, e.getMessage(), e);
            throw new SystemException(e.getMessage());
        }
    }
}
