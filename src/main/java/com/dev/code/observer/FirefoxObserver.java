package com.dev.code.observer;

/**
 * @author liaonanzhou
 * @date 2020-11-26 15:50
 * @description
 */
public class FirefoxObserver extends Observer {

    public FirefoxObserver(Subject subject){
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void call() {
        System.out.println("i am firefox abserver "+ this.subject.getState());
    }
}
