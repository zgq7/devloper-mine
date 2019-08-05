package com.dev.config;

import com.dev.config.aop.BaseAop;
import com.dev.config.aop.RuntimeExceptionAspectJ;
import com.dev.filter.BaseFilter;
import com.dev.utils.email.MailSendUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.*;

/**
 * Created by zgq7 on 2019/6/6.
 * 注册一些bean进入ioc
 *
 * @EnableAspectJAutoProxy 开启aop代理
 */
@Configuration
@EnableAspectJAutoProxy
public class BeanRegistryCenterConfig {

    /**
     * BaseFilter bean 注册
     **/
    @Bean
    public FilterRegistrationBean<BaseFilter> filterFilterRegistrationBean(BaseFilter baseFilter) {
        FilterRegistrationBean<BaseFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        //拦截路径配置
        List<String> uriList = new ArrayList<>(10);
        uriList.add("/dev/*");

        filterFilterRegistrationBean.setFilter(baseFilter);
        filterFilterRegistrationBean.setEnabled(true);
        filterFilterRegistrationBean.setUrlPatterns(uriList);
        filterFilterRegistrationBean.setName("baseFilter");
        filterFilterRegistrationBean.setOrder(1);

        return filterFilterRegistrationBean;
    }


    /**
     * 邮箱工具类 bean 注册
     **/
    @Bean
    public MailSendUtils mailSendUtils() {
        return new MailSendUtils();
    }

    /**
     * 本地线程 bean 注册
     **/
    @Bean(name = LocalThreadPool.PACKAGE_BEAN_NAME)
    public LocalThreadPool localThreadPool() {
        return new LocalThreadPool();
    }

    /**
     * BaseAop bean 注册
     **/
    @Bean
    public BaseAop baseAop() {
        return new BaseAop();
    }

    /**
     * 异常捕获类 RuntimeExceptionAspectJ bean 注册
     **/
    @Bean
    public RuntimeExceptionAspectJ runtimeExceptionAspectJ() {
        return new RuntimeExceptionAspectJ();
    }

}
