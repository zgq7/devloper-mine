package com.dev.code.observer;

import java.util.Vector;

/**
 * @author liaonanzhou
 * @date 2020-11-26 15:45
 * @description
 */
public class Subject {

    private Vector<Observer> observers = new Vector<>();

    private int state;

    public void setState(int state) {
        this.state = state;
        notifyAllObserver();
    }

    public int getState() {
        return state;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void notifyAllObserver() {
        for (Observer observer : observers) {
            observer.call();
        }
    }



}
