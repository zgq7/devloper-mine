package com.dev.code.atomic;

import java.util.concurrent.TimeUnit;

/**
 * @author zgq7
 * @date 2020-08-04 12:08
 **/
public class VolatileTest {

    private static volatile int i = 0;

    //原因：volatile 修饰的变量进行非原子操作时则会出现线程安全问题
    //解决办法：通过synchronized 关键字保证原子性操作
    public static void main(String[] args) {
        for (int t = 0; t < 1000; t++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "->" + i);
                }
            }, String.valueOf(t)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
