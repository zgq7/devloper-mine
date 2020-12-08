package com.dev.code;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaonanzhou
 * @date 2020/9/16 18:17
 * @description
 **/
public class LockMain {

    private static final Logger logger = LoggerFactory.getLogger(LockMain.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lock lock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            final int name = i;
            executorService.execute(() -> {
                Thread.currentThread().setName(String.valueOf(name));
                try {
                    lock.lock();
                    TimeUnit.SECONDS.sleep(1);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info(Thread.currentThread().getName() + "：sleep over");
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
