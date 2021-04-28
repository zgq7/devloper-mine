package com.loper.mine.core.design.factory.abs;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 * @description 抽象工厂模式
 * 1：抽象工厂
 * 2：具体工厂
 * 3：抽象产品
 * 4：具体产品
 * 和工厂方法模式的不同处：
 * 1：工厂方法某个工厂只生产/针对某个产品
 * 2：抽象工厂针对的是产品组
 **/
public class AbsFactoryTest {

    public static void main(String[] args) {
        Factory factoryA = new FactoryA();

        factoryA.location();
        factoryA.tv();

        System.out.println("========================");

        Factory factoryB = new FactoryB();
        factoryB.location();
        factoryB.tv();

    }

}
