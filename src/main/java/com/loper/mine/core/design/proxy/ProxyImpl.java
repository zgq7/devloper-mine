package com.loper.mine.core.design.proxy;

/**
 * @author liaonanzhou
 * @date 2021/4/19 14:28
 * @description
 **/
public class ProxyImpl implements ProxyInterface {

    private String threadInfo = null;

    @Override
    public void printConcurrentThreadInfo() {
        System.out.println("print info = " + threadInfo);
    }

    @Override
    public void setConcurrentThreadInfo(String threadInfo) {
        System.out.println("set info = " + threadInfo);
        this.threadInfo = threadInfo;
    }

}
