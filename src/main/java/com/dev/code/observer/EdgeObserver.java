package com.dev.code.observer;

/**
 * @author liaonanzhou
 * @date 2020-11-26 15:54
 * @description
 */
public class EdgeObserver extends Observer {

    public EdgeObserver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void call() {
        System.out.println("i am edge observer " + this.subject.getState());
    }
}
