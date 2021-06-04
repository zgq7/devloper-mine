package com.loper.mine;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author liaonanzhou
 * @date 2020-11-26 14:30
 * @description
 */
public class JUCTest {

    private static final Logger logger = LoggerFactory.getLogger(JUCTest.class);

    /**
     * 来自于阿里：
     * <p>
     * 「请寻求最优解，不要只是粗暴wait（）」
     * 有一个总任务A，分解为子任务A1 A2 A3 ...，任何一个子任务失败后要快速取消所有任务，请写程序模拟。
     **/
    @Test
    public void jucTest01() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                logger.info(Thread.currentThread().getName());
                throw new RuntimeException("juc error");
            } catch (Exception e) {
                countDownLatch.countDown();
            }
        }, "thread-1");

        Thread t2 = new Thread(() -> {
            try {
                logger.info(Thread.currentThread().getName());
            } catch (Exception e) {
                countDownLatch.countDown();
            }
        }, "thread-2");

        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                logger.info(Thread.currentThread().getName());
            } catch (Exception e) {
                countDownLatch.countDown();
            }
        }, "thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        logger.info("------------TASK OVER-------");
        logger.info("该方式不可用，只是取消该次任务，不可将子线程kill掉");
    }


    @Test
    public void jucTest02() throws InterruptedException {
        int cap = 10;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cap, cap, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        CountDownLatch latch = new CountDownLatch(2);
        Semaphore semaphore = new Semaphore(0);

        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
        });

        executor.execute(() -> {
            try {
                while (true){
                    boolean ack = semaphore.tryAcquire();
                    if (ack){
                        System.out.println("acked...");
                        break;
                    }else {
                        System.out.println("...");
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            }catch (Exception ignore){

            }finally {
                latch.countDown();
            }
        });

        latch.await();
        executor.shutdown();

    }


}
