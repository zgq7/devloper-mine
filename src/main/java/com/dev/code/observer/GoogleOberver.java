package com.dev.code.observer;

/**
 * @author liaonanzhou
 * @date 2020-11-26 15:48
 * @description
 */
public class GoogleOberver extends Observer {

    public GoogleOberver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void call() {
        System.out.println("i am google observer " + this.subject.getState());
    }
}
