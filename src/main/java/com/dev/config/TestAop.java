package com.dev.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;


/**
 * Created by zgq7 on 2019/7/12.
 * 利用aop机制做一个触发
 * <p>
 * 在切入点前的操作，按order的值由小到大执行
 * 在切入点后的操作，按order的值由大到小执行
 */
@Aspect
@Order(1)
public class TestAop {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 写入具体切面点
     **/
    @Pointcut("execution(public * com.dev.service.AopiService.getList() )")
    public void aspect() {
        log.info("aop aspect start ...");
    }

    /**
     * 进入方法体前
     **/
    @Before(value = "aspect()")
    public void before(JoinPoint joinPoint) {
        log.info("before ：参数类型：{}", joinPoint.getArgs());
    }

    /**
     * 该切面返回数据前（在retrun之前执行）
     **/
    @After(value = "aspect()")
    public void after(JoinPoint joinPoint) {
        log.info("aop before return ：返回类型：{}", joinPoint.getSignature());
    }

    /**
     * 该切面返回数据后
     * joinPoint.getSignature() 返回方法放回类型
     **/
    @AfterReturning(value = "aspect()", returning = "result")
    public void afterReturning(Object result) {
        log.info("aop after return ：返回结果：{}", result);
    }

    /**
     * 环绕通知
     * 1：before之前
     * 2：afterReturning之后
     **/
    @Around(value = "aspect()")
    public void around(JoinPoint joinPoint) {
        log.info("aop arrounding ：{}");
    }

    /**
     * 切面报错
     **/
    @AfterThrowing(value = "aspect()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("aop exception ：{}", e);
    }


}
