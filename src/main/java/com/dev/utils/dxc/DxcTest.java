package com.dev.utils.dxc;

import java.util.concurrent.CountDownLatch;

/**
 * Created on 2019-09-23 22:32.
 *
 * @author zgq7
 */
public class DxcTest {

    public static void main(String[] args) {
        //等待两个线程
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(()->{
            hello();
            countDownLatch.countDown();
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
                hi();
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(DxcTest::over);

        t1.start();
        t2.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        over();

    }

    public static void hello() {
        System.out.println("hello");
    }

    public static void hi() {
        System.out.println("hi");
    }

    public static void over() {
        System.out.println("over");
    }
}
