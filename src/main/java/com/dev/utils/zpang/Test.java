package com.dev.utils.zpang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created on 2019-09-27 22:22.
 *
 * @author zgq7
 * @apiNote 1:类的内部类可以单继承多实现
 * 2:抽象类继承抽象类可以不实习父类的抽象方法
 * 3:子类不可访问父类的私有属性
 * 4:内部类继承接口时可不实现抽象方法，普通类必须实现
 */
public class Test extends AbstractTest implements TestWithInterface, TestInterface {

    public static void main(String[] args) {
        try {
            TestInterface t2 = (TestInterface) Class.forName("com.dev.utils.zpang.TestImpl01").newInstance();
            Method method = t2.getClass().getMethod("test03", Integer.class);
            Object reslut = method.invoke(t2,9);
            System.out.println(reslut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int test9() {
        try {
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            return 3;
        }
    }

    @Override
    public void test() {
        System.out.println(super.name);
    }

    @Override
    public Integer test03(Integer p) {
        return null;
    }

    public class child extends TestImpl01 implements TestInterface, TestWithInterface {

    }

    public class child2 extends AbstractTest {
        @Override
        public void test() {
        }

}
}
