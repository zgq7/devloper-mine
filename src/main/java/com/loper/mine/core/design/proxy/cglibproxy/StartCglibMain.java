package com.loper.mine.core.design.proxy.cglibproxy;

import com.loper.mine.core.design.proxy.ProxyImpl;
import com.loper.mine.core.design.proxy.ProxyInterface;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author liaonanzhou
 * @date 2021/4/19 14:38
 * @description
 **/
public class StartCglibMain {

    public static void main(String[] args) {
        CglibProxyInstance cglibProxyInstance = new CglibProxyInstance();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyImpl.class);
        enhancer.setCallback(cglibProxyInstance);

        ProxyInterface proxyInterface = (ProxyInterface) enhancer.create();

        proxyInterface.setConcurrentThreadInfo("cglib proxy ");
    }
}
