package com.loper.mine.core.design.proxy;

/**
 * @author liaonanzhou
 * @date 2021/4/19 14:26
 * @description 代理接口
 * @apiNote 代理失效场景
 * 1：方法非public 修饰
 * 2：方法中调用同类的方法
 **/
public interface ProxyInterface {

    void printConcurrentThreadInfo();

    void setConcurrentThreadInfo(String threadInfo);

}
