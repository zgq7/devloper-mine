package com.loper.mine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;

/**
 * @author zgq7
 * @version 1.0
 * @date 2020/4/14 11:06
 **/
//@Component
public class ApplicationConfig implements ApplicationListener<ApplicationStartedEvent>, InitializingBean, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	private static ApplicationContext application;
	@Resource
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("xxxx -》 {}", applicationContext);
		logger.info("xxxx -》 {}", application);
		logger.info("xxxx -》 {}", application == applicationContext);
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
		logger.info("容器启动！");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		application = applicationContext;
	}
}
