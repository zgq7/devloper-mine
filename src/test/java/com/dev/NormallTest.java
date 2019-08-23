package com.dev;

import com.dev.config.LocalThreadPool;
import com.dev.utils.zpang.TestImpl01;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created on 2019-07-30 10:59.
 *
 * @author zgq7
 */
public class NormallTest {

    /**
     * TimeUtils 类的功能测试
     **/
    @Test
    public void test01() throws Exception {
        //System.out.println(String.format("asst:captcha:%s", "2222"));
        //System.out.println(RandomStringUtils.random(6));

        Class klass = Class.forName(TestImpl01.PACKAGE_PATH);
        System.out.println(klass);
        Object intance = klass.newInstance();
        System.out.println(intance);

        Method method = klass.getMethod("test03", Integer.class);
        Method method2 = klass.getMethod("test2");
        System.out.println(method);

        method.invoke(intance, 1);
        method2.invoke(intance);

    }

    @Test
    public void test02() {
        Thread thread1 = new Thread(() -> {
            LocalThreadPool localThreadPool1 = LocalThreadPool.getInstance();
            System.out.println(localThreadPool1.hashCode());
        });
        Thread thread2 = new Thread(() -> {
            LocalThreadPool localThreadPool2 = LocalThreadPool.getInstance();
            System.out.println(localThreadPool2.hashCode());
        });

        thread1.start();
        thread2.start();

        try {
            synchronized (Thread.currentThread()) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() {
        // 针对二进制，转换成二进制后向左移动n位，后面用0补齐
        System.out.println(1 << 0);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println("=================================");
        // 针对二进制，只要有一个余数为0，就为0
        System.out.println(1 & 5);
        System.out.println(5 & 1);
        System.out.println(1 & 4);
        System.out.println(4 & 1);
        System.out.println("=================================");
        System.out.println(5 & ~5);
        System.out.println(5 & ~4);
        System.out.println("=================================");
        System.out.println(5 | 4);
        System.out.println(1 | 8);
    }

}
