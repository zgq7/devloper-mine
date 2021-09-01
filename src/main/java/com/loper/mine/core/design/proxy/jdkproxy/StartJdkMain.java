package com.loper.mine.core.design.proxy.jdkproxy;

import com.loper.mine.core.design.proxy.ProxyImpl;
import com.loper.mine.core.design.proxy.ProxyInterface;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author liaonanzhou
 * @date 2021/4/19 14:38
 * @description
 **/
public class StartJdkMain {

    public static void main(String[] args) {
        ProxyImpl proxyImp = new ProxyImpl();
        JdkProxyInstance jdkProxyInstance = new JdkProxyInstance(proxyImp);
        Class<?> clazz = proxyImp.getClass();

        ProxyInterface proxyInterface = (ProxyInterface) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), jdkProxyInstance);
        proxyInterface.setConcurrentThreadInfo("jdk proxy");

    }
}
