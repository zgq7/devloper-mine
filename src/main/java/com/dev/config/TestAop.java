package com.dev.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zgq7 on 2019/7/12.
 * 利用aop机制做一个触发
 */
@Aspect
public class TestAop {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.dev.mapper.mappers.AopiMapper.selectAll())")
    public void aspect() {
        log.info("aop aop 开始...");
    }

    @Before("aspect()")
    public void requestStartBefore(JoinPoint joinPoint) {
        log.info("before ： {}");
    }

    @After("aspect()")
    public void requestFinishedAfter(JoinPoint joinPoint) {
        log.info("aop after ： {}");
    }

    @AfterReturning("aspect()")
    public void requestRunning(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("aop afterReturnning ： {},{}", args, joinPoint.getSignature());
    }

    @Around("aspect()")
    public void requestAround(JoinPoint joinPoint) {
        log.info("aop arrounding ： {}");
    }

    @AfterThrowing(value = "aspect()", throwing = "e")
    public void requestException(JoinPoint joinPoint, Exception e) {
        log.info("aop exception ： {}", e);
    }
}
