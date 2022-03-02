package com.loper.mine.core.design.proxy.cglibproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liaonanzhou
 * @date 2021/9/1 10:43
 * @description
 **/
public class CglibProxyInstance implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("thread local working start ");
        Object proxyReturn = proxy.invokeSuper(obj, args);
        System.out.println("thread local working end.... ");
        return proxyReturn;
    }
}
