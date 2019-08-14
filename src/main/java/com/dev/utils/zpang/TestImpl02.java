package com.dev.utils.zpang;


/**
 * Created on 2019-08-08 15:35.
 *
 * @author zgq7
 */
public class TestImpl02 implements TestInterface {

    public static String PACKAGE_PATH = "com.dev.utils.zpnag.TestImpl02";

    @Override
    public void test2() {
        System.out.println("testImpl02 of test02");
    }

    @Override
    public Integer test03(Integer p) {
        System.out.println("testImpl02 of test03");
        return p;
    }
}
