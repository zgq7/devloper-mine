package com.loper.mine.config;

import com.loper.mine.config.aop.BaseAop;
import com.loper.mine.config.aop.RuntimeExceptionAspectJ;
import com.loper.mine.config.filter.BaseFilter;
import com.loper.mine.utils.email.MailSendUtils;
import com.loper.mine.utils.websocket.SocketManager;
import com.loper.mine.utils.websocket.SocketServer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
		uriList.add("/*");

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
		return LocalThreadPool.getInstance();
	}

	/**
	 * BaseAop bean 注册
	 **/
	//@Bean
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

	/**
	 * WebScoketManager Bean 注册
	 **/
	@Bean
	public SocketManager socketManager() {
		return new SocketManager();
	}

	/**
	 * webSocket session 容器 Bean
	 **/
	@Bean
	public SocketServer socketServer() {
		return new SocketServer();
	}

	/**
	 * 开启webSocket 容器管理
	 * 如果不用springboot 使用 独立的 tomcat则不需要此bean
	 **/
	//@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
