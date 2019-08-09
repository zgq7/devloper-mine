package com.dev.utils.zpnag;

/**
 * Created on 2019-08-08 15:10.
 *
 * @author zgq7
 */
public class TestImpl01 implements TestInterface {

    public static String PACKAGE_PATH = "com.dev.utils.zpnag.TestImpl01";

    @Override
    public void test2() {
        System.out.println("testImpl01 of test02");
    }

    @Override
    public Integer test03(Integer p) {
        System.out.println("testImpl01 of test03");
        return p;
    }


}
