package com.dev.code.observer;

/**
 * @author liaonanzhou
 * @date 2020-11-26 15:44
 * @description
 */
public abstract class Observer {

    protected Subject subject;

    public abstract void call();
}
