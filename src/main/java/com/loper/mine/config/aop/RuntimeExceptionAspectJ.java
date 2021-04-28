package com.loper.mine.config.aop;

import com.loper.mine.config.LocalThreadPool;
import com.loper.mine.model.email.EmailModel;
import com.loper.mine.utils.email.MailSendUtils;
import com.loper.mine.utils.exception.ExceptionCodes;
import com.loper.mine.utils.exception.ServiceException;
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
    @Pointcut("execution(public * com.loper.mine.controller.TestController.*(..))")
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
        emailModel.setRecieverName("陈祖欧");
        emailModel.setEmailContent(exception.toString() + ":\n" + Arrays.toString(exception.getStackTrace()));
        emailModel.setRecieverEmailAddress("1062406029@qq.com");
        //同步发送
        //mailSendUtils.sendEmailAsSysExceptionHtml(emailModel);
        //异步发送
        //localThreadPool.threadPoolExecutor.execute(() -> mailSendUtils.sendEmailAsSysExceptionHtml(emailModel));
        throw new ServiceException(ExceptionCodes.getCodeByKlass(klass), ExceptionCodes.getMsgByKlass(klass));
    }

}
