package com.loper.mine.core.design.proxy.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @author liaonanzhou
 * @date 2022/7/19 18:02
 * @description
 **/
public class JdkProxyFactory {
    private final Object targetProxy;

    public JdkProxyFactory(Object targetProxy) {
        this.targetProxy = targetProxy;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(targetProxy.getClass().getClassLoader(), targetProxy.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("thread local working start ");
            Object proxyReturn = method.invoke(targetProxy, args);
            System.out.println("thread local working end.... ");
            return proxyReturn;
        });
    }
}
