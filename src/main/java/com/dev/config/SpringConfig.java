package com.dev.config;

import com.dev.filter.BaseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


/**
 * Created by zhugenqi on 2019/6/6.
 */
@Configuration
public class SpringConfig {

    @Bean
    public FilterRegistrationBean<BaseFilter> filterFilterRegistrationBean(BaseFilter baseFilter) {
        FilterRegistrationBean<BaseFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        List<String> uriList = new ArrayList<>(10);
        uriList.add("/dev/*");

        filterFilterRegistrationBean.setFilter(baseFilter);
        filterFilterRegistrationBean.setEnabled(true);
        filterFilterRegistrationBean.setUrlPatterns(uriList);
        filterFilterRegistrationBean.setName("baseFilter");
        filterFilterRegistrationBean.setOrder(1);

        return filterFilterRegistrationBean;
    }

}
