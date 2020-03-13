package com.dev.utils.zpang;

/**
 * Created on 2019-08-08 14:38.
 *
 * @author zgq7
 * @apiNote 1:static  修饰的接口方法不用被实现
 * 2:default 修饰的接口方法可被实现，也可以不实现
 * 3:无static、default 修饰的接口没有代码体，必须被实现
 */
public interface TestInterface {

    static void test01() {
        System.out.println(1);
    }

    default void test2() {
        System.out.println(2);
    }

    Integer test03(Integer p);

}
