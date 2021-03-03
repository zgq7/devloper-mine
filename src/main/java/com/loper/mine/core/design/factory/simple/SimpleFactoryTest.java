package com.loper.mine.core.design.factory.simple;

/**
 * @author zgq7
 * @date 2020-08-11 16:08
 * @description 简单工厂模式
 * 1：具体工厂类
 * 2：抽象产品类
 * 3：具体产品类
 * 缺点：
 * 1：耦合严重，工厂异常将可能导致不能工作
 * 2：扩展困难，新增产品需要修改工厂
 **/
public class SimpleFactoryTest {

    public static void main(String[] args) {

        CarProductFactory.produceCarByName("汽车");

        CarProductFactory.produceCarByName("飞机");

        CarProductFactory.produceCarByName(null);

    }


}
