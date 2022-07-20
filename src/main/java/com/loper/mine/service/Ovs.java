package com.loper.mine.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liaonanzhou
 * @date 2022/7/18 16:29
 * @description
 **/
@Service
public class Ovs implements InitializingBean {
    @Resource
    private ProxyService proxyService;


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ovs:" + proxyService.hashCode());
    }
}
