package com.dev.code.factory.method;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 * @description 工厂方法模式
 * 1：抽象工厂
 * 2：具体工厂
 * 3：抽象产品
 * 4：具体产品
 * 缺点：
 * 1：添加新产品时，需要新增抽象产品、具体产品类个数
 * 2：引入了抽象层，增加了系统理解难度，实现时可能要用到反射、DOM 等技术，实现难度高
 **/
public class FactoryMethodTest {

    public static void main(String[] args) {

        Factory carFactory = new CarFactory();

        Factory aircraftFactory = new AircraftFactory();

        carFactory.product();

        aircraftFactory.product();
    }

}
