package com.dev.code.atomic;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author zgq7
 * @date 2020-08-04 13:08
 **/
public class SynchronizedTest {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "获取锁开始" + LocalDateTime.now());
                synchronized (SynchronizedTest.class) {
                    if (Thread.currentThread().getName().equals("0")) {
                        try {
                            TimeUnit.SECONDS.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(Thread.currentThread().getName() + "获取锁结束" + LocalDateTime.now());
            }, String.valueOf(i)).start();
        }

    }
}
