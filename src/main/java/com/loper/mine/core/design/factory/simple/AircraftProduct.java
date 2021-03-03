package com.loper.mine.core.design.factory.simple;

/**
 * @author zgq7
 * @date 2020-08-11 16:08
 **/
public class AircraftProduct implements Product {
    @Override
    public void produceCar() {
        System.out.println("生产飞机...");
    }
}
