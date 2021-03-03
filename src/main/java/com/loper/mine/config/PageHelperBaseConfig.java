package com.loper.mine.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/2 18:28
 **/
//@Configuration
public class PageHelperBaseConfig {

	@Bean
	public Properties pageProperties() {
		Properties properties = new Properties();
		//把这个设置为true，会带RowBounds第一个参数offset当成PageNum使用
		properties.setProperty("offsetAsPageNum", "true");
		//设置为true时，使用RowBounds分页会进行count查询
		properties.setProperty("rowBoundsWithCount", "true");
		//true 查询的页没有数据则显示最后一页的数据 false 直接参数分页
		properties.setProperty("reasonable", "false");
		properties.setProperty("supportMethodsArguments", "true");
		return properties;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		Interceptor[] plugins = new Interceptor[1];
		PageInterceptor pageInterceptor = new PageInterceptor();
		pageInterceptor.setProperties(pageProperties());
		plugins[0] = pageInterceptor;
		sqlSessionFactoryBean.setPlugins(plugins);
		sqlSessionFactoryBean.setDataSource(druidDataSource());
		return sqlSessionFactoryBean;
	}

	@Bean
	public DruidDataSource druidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/dsos_zgq?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("root");
		druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return druidDataSource;
	}

}
