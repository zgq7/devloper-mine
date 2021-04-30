package com.dev.code;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaonanzhou
 * @date 2020/9/29 17:30
 * @description
 **/
public class SortLock {

    private static Lock lock = new ReentrantLock();

    private static Condition a = lock.newCondition();
    private static Condition b = lock.newCondition();
    private static Condition c = lock.newCondition();

    private static volatile int x = 1;

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) {
        System.out.println("打乱 a b c 的获取锁顺序 ...");

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                lock.lock();
                System.out.println("a 拿到锁");
                countDownLatch.countDown();
                while (true) {
                    if (x == 1) {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("a");
                        x = 2;
                        b.signal();
                    } else {
                        a.await();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("b 拿到锁");
                countDownLatch.countDown();
                while (true) {
                    if (x == 2) {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("b");
                        x = 3;
                        c.signal();
                    } else {
                        b.await();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("c 拿到锁");
                countDownLatch.countDown();
                while (true) {
                    if (x == 3) {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("c");
                        x = 1;
                        a.signal();
                    } else {
                        c.await();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        try {
            countDownLatch.await();
            System.out.println("全部获取到锁，准备输出 a b c ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
