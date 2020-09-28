package com.dev.code;

import com.dev.config.LocalThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaonanzhou
 * @date 2020/9/16 18:17
 * @description
 **/
public class Lockk {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lock lock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            final int name = i;
            executorService.execute(() -> {
                Thread.currentThread().setName(String.valueOf(name));
                try {
                    lock.lock();
                    TimeUnit.SECONDS.sleep(3);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "：sleep over");
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdownNow();
        System.out.println("over");
    }
}
