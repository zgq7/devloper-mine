package com.loper.mine.core.design.factory.simple;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 **/
public class CarProductFactory {

    public static void produceCarByName(String name) {
        Product product;

        if ("汽车".equals(name)) {
            product = new CarProduct();
        } else if ("飞机".equals(name)) {
            product = new AircraftProduct();
        } else {
            System.out.println("没有 " + name + " 产品");
            return;
        }

        product.produceCar();
    }

}
