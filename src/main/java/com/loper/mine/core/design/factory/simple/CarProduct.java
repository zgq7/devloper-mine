package com.loper.mine.core.design.factory.simple;

/**
 * @author zgq7
 * @date 2020-08-11 16:08
 **/
public class CarProduct implements Product {
    @Override
    public void produceCar() {
        System.out.println("生产汽车...");
    }
}
