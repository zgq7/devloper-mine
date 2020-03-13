package com.dev.utils.zpang;

/**
 * Created on 2019-09-27 22:18.
 *
 * @author zgq7
 *
 * @apiNote
 * 1:抽象类可以没有抽象方法，但抽象方法必须在抽象类中
 * 2:抽象方法后面不用谢{}
 * 3:不可用final修饰抽象类以及抽象方法,不可用abstract修饰属性
 * 4:final修饰的属性必须初始化
 */
public abstract class AbstractTest {

    public final String name = "test";

    public abstract void test();

    public  void test2(){
        System.out.println(1);
    }
}
