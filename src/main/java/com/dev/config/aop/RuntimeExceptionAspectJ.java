package com.dev.config.aop;

import com.dev.utils.exception.ExceptionCodes;
import com.dev.utils.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * Created on 2019-07-31 9:41.
 *
 * @author zgq7
 */
@Aspect
@Order(2)
public class RuntimeExceptionAspectJ {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //@Pointcut("execution(public * com.dev..*(..))")
    @Pointcut("execution(public * com.dev.controller.TestController.*(..))")
    private void runtimeExceptionAspect() {
    }

    /**
     * 进入方法体前
     **/
    @Before(value = "runtimeExceptionAspect()")
    public void before(JoinPoint joinPoint) {
        log.info("before ：参数类型：{}", joinPoint.getArgs());
    }

    /**
     * 该切面返回数据前（在retrun之前执行）
     **/
    @After(value = "runtimeExceptionAspect()")
    public void after(JoinPoint joinPoint) {
        log.info("aop before return ,kind :{}", joinPoint.getKind());
    }

    /**
     * 该切面返回数据后
     * joinPoint.getSignature() 返回方法放回类型
     **/
    @AfterReturning(value = "runtimeExceptionAspect()", returning = "result")
    public void afterReturning(Object result) {
        log.info("aop after return ：返回结果：{}", result);
    }

    /**
     * 切面报错
     **/
    @AfterThrowing(value = "runtimeExceptionAspect()", throwing = "exception")
    public void afterThrowing(Throwable exception) {
        Class klass = exception.getClass();
        log.error("occured a [{}] , msg : [{}]", klass.getSimpleName(), ExceptionCodes.getMsgByKlass(klass));
        throw new ServiceException(ExceptionCodes.getCodeByKlass(klass), ExceptionCodes.getMsgByKlass(klass));
    }

}
