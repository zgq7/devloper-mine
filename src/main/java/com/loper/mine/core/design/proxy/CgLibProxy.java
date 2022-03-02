package com.loper.mine.core.design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zgq7
 * @date 2020-08-12 14:08
 **/
public class CgLibProxy {

    public static void main(String[] args) {
        CglibInnerProxy innerProxy = new CglibInnerProxy();
        CgLibProxy proxy = (CgLibProxy) innerProxy.getProxy(CgLibProxy.class);
        proxy.sayHello();
    }

    public static class CglibInnerProxy implements MethodInterceptor {

        private Enhancer enhancer = new Enhancer();

        public Object getProxy(Class clazz) {
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("cglib proxy interceptor start ...");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("cglib proxy interceptor end ...");
            return result;
        }
    }

    public void sayHello() {
        System.out.println("hello");
    }


}
