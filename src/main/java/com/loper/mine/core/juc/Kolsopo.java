package com.loper.mine.core.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liaonanzhou
 * @date 2021-03-16 10:23
 * @description
 */
public class Kolsopo {

    private static AtomicLong NUM = new AtomicLong(2);
    private static AtomicLong SUM = new AtomicLong(1);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                long sum, num;
                do {
                    sum = SUM.get();
                    num = NUM.get();
                    SUM.compareAndSet(sum, sum * num);
                } while (SUM.compareAndSet(sum, sum * num));
                System.out.println("线程：" + Thread.currentThread().getName() + "，index=" + i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                long sum, num;
                do {
                    sum = SUM.get();
                    num = NUM.get();
                    SUM.compareAndSet(sum, sum * num);
                } while (SUM.compareAndSet(sum, sum * num));
                System.out.println("线程：" + Thread.currentThread().getName() + "，index=" + i);
            }
        }, "B").start();

        TimeUnit.SECONDS.sleep(10);

        System.out.println(4 * (2 << 19 ));
        System.out.println(4 * SUM.get());

    }

}
