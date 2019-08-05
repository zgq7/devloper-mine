package com.dev.config.aop;

import com.dev.config.LocalThreadPool;
import com.dev.model.email.EmailModel;
import com.dev.utils.email.MailSendUtils;
import com.dev.utils.exception.ExceptionCodes;
import com.dev.utils.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * Created on 2019-07-31 9:41.
 *
 * @author zgq7
 */
@Aspect
@Order(2)
public class RuntimeExceptionAspectJ {
    @Autowired
    private MailSendUtils mailSendUtils;

    @Autowired
    private LocalThreadPool localThreadPool;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //@Pointcut("execution(public * com.dev..*(..))")
    @Pointcut("execution(public * com.dev.controller.TestController.*(..))")
    private void runtimeExceptionAspect() {
    }

    /**
     * 切面报错
     **/
    @AfterThrowing(value = "runtimeExceptionAspect()", throwing = "exception")
    public void afterThrowing(Throwable exception) {
        Class klass = exception.getClass();
        log.error("occured a [{}] , msg : [{}]", klass.getSimpleName(), ExceptionCodes.getMsgByKlass(klass));
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailTheme("测试");
        emailModel.setRecieverName("董昕杰");
        emailModel.setEmailContent(exception.toString()+":\n"+ Arrays.toString(exception.getStackTrace()));
        emailModel.setRecieverEmailAddress("3110320051@qq.com");
        //emailModel.setRecieverEmailAddress("1140661106@qq.com");

        mailSendUtils.sendEmailAsSysExceptionHtml(emailModel);
        throw new ServiceException(ExceptionCodes.getCodeByKlass(klass), ExceptionCodes.getMsgByKlass(klass));
    }

}
