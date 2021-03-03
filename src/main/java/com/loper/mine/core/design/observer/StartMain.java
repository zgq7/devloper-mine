package com.loper.mine.core.design.observer;

/**
 * @author liaonanzhou
 * @date 2020-11-26 16:03
 * @description
 */
public class StartMain {

    public static void main(String[] args) {
        Subject subject = new Subject();

        GoogleOberver googleOberver = new GoogleOberver(subject);
        FirefoxObserver firefoxObserver = new FirefoxObserver(subject);
        EdgeObserver edgeObserver = new EdgeObserver(subject);

        System.out.println("first update state");
        subject.setState(10);

        System.out.println("second update state");
        subject.setState(20);
    }
}
