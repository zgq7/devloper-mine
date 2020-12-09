package com.dev.juc.corresponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaonanzhou
 * @date 2020-12-04 18:04
 * @description 线程通信
 */
public class StartMain {

    private static final Logger logger = LoggerFactory.getLogger(StartMain.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static final String[] arr = new String[]{"A1", "B2", "C3", "D4"};
    private static final AtomicInteger index = new AtomicInteger(0);

    public static void main(String[] args) {
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();

        Thread threadA = new Thread(() -> {
            while (index.get() < arr.length) {
                try {
                    lock.lock();
                    logger.info(arr[index.get()]);
                    index.incrementAndGet();
                    conditionB.signal();
                    conditionA.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "thread-A");

        Thread threadB = new Thread(() -> {
            while (index.get() < arr.length) {
                try {
                    lock.lock();
                    conditionB.await();
                    logger.info(arr[index.get()]);
                    index.incrementAndGet();
                    conditionA.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "thread-B");

        threadB.start();

        // 为了使测试更加逼真，先让B开始
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadA.start();
    }

}
