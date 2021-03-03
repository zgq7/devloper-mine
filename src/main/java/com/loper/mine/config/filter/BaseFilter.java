package com.loper.mine.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhugenqi on 2019/6/6.
 */
@Component
public class BaseFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(BaseFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Map<Object, Object> map = new LinkedHashMap<>(10);
        map.put("filterName", filterConfig.getFilterName());

        log.info("filter config indclude :{}", map);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        Map<Object, Object> map = new HashMap<>(10);
        map.put("url", request.getRequestURL());
        map.put("uri", uri);
        map.put("requestType", request.getMethod());
        log.info("{}", map);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("filter destroyed ...");
    }
}
