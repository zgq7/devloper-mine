package com.dev.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.util.Collections;

/**
 * Created by zgq7 on 2019/7/12.
 * <p>
 * 在切入点前的操作，按order的值由小到大执行
 * 在切入点后的操作，按order的值由大到小执行
 * <p>
 * 各通知执行顺序：
 * 1:around->
 * 2:before->
 * 3:around->retrun code ->
 * 4:after->
 * 5:afterReturning->
 * 6:afterThrowing
 * </p>
 * <p>
 * order 执行顺序：
 * 1：进入目的方法时：order值越小越先执行
 * 2：从目的方法出去时：order值越大越先执行
 * </p>
 */
@Aspect
@Order(1)
public class BaseAop {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 写入具体切面点
     *
     * @apiNote execution 路径中的 "." 如果是精确到类，则一个就行，如果是精确到包就得两个 "."
     * @apiNote execution 路径若只精确到包,就无法使用 before、after、afterReturuning、around 四个通知模块，启动会报错
     **/
    @Pointcut("execution(public * com.dev.controller.TestController.s())")
    public void aspect() {
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
        log.info("aop before return ：{}", joinPoint.toLongString());
    }

    /**
     * 该切面返回数据后
     * joinPoint.getSignature() 返回方法放回类型 及 方法路径
     **/
    @AfterReturning(value = "aspect()", returning = "result")
    public void afterReturning(Object result) {
        log.info("aop after return ：返回结果：{}", result);
    }

    /**
     * 环绕通知
     * 1：before之前
     * 2：afterReturning之后
     *
     * @apiNote 1：@Around 下接的方法的参数必须为 ProceedingJoinPoint 类型,
     * @apiNote 2：proceedingJoinPoint.proceed() 产生的结果即为  @AfterReturning 中的 result,可 debug 调试
     * @apiNote 3：由于@Around要提前获取到目的方法的执行结果，且@Around提前于@AfterThrowing运行，因此异常将在@Around中被捕获，从而导致@AfterThrowing捕获不到异常，因此@Around与@AfterThrowing混合使用
     **/
    //@Around(value = "aspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("aop arrounding ：{}", proceedingJoinPoint.getSignature().getName());
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.info("aop arrounding error ：{}", throwable.getMessage());
            //throwable.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }

    /**
     * 切面报错
     **/
    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterThrowing(Throwable exception) {
        log.error("exception occured , msg : {}", exception.getMessage());
        if (exception instanceof NullPointerException)
            log.info("空指针异常");
    }

}
