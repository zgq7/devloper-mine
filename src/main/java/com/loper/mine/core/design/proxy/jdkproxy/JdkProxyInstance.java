package com.loper.mine.core.design.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liaonanzhou
 * @date 2021/4/19 14:31
 * @description
 **/
public class JdkProxyInstance implements InvocationHandler {

    private final Object targetProxy;

    public JdkProxyInstance(Object targetProxy) {
        super();
        this.targetProxy = targetProxy;
    }

    /**
     * @param proxy  被代理对象，实际为：java.lang.reflect.Proxy 类的对象
     * @param method 被代理对象的方法
     * @param args   参数
     * @return 被代理方法的返回值
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("thread local working start ");
        Proxy pp = (Proxy) proxy;
        Object proxyReturn = method.invoke(targetProxy, args);
        System.out.println("thread local working end.... ");
        return proxyReturn;
    }

}
