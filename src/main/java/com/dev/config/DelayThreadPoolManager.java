package com.dev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020/9/28 15:14
 * @description
 **/
public class DelayThreadPoolManager {

    private static final Logger logger = LoggerFactory.getLogger(DelayThreadPoolManager.class);

    private static final int COUNT = 6;

    private static final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(COUNT);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(1);

        logger.info("start");

        //executeSchedule();

        executeForeach();

        try {
            downLatch.await();
            scheduledThreadPoolExecutor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Runnable execute(final int sno, final boolean next) {
        return () -> {
            logger.info(String.valueOf(sno));
            if (next){
                if (atomicInteger.incrementAndGet() == COUNT) {
                    logger.info("next");
                    atomicInteger.set(0);
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //downLatch.countDown();
        };
    }

    /**
     * 每分钟执行一次
     * 18:60:00 execute command ...
     * 18:60:00 execute command ...
     **/
    private static void executeSchedule() {
        LocalDateTime now = LocalDateTime.now();
        long millis = Duration.between(now, now.plusMinutes(1).minusSeconds(now.getSecond())).toMillis();
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(execute(1, false), millis, 60 * 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 批量执行
     * 每六个线程执行完输出 next ... 再继续执行
     **/
    private static void executeForeach() {
        for (int i = 1; i <= 1; i++) {
            // 延迟执行 延迟六秒
            //scheduledThreadPoolExecutor.schedule(runnable, 6, TimeUnit.SECONDS);

            // 限速执行 当前线程执行完任务之后  需[间隔6秒]再重复执行当前任务  不管上份任务是否执行完毕
            scheduledThreadPoolExecutor.scheduleAtFixedRate(execute(i, true), 3, 6, TimeUnit.SECONDS);

            // 限速执行 当前线程执行完任务之后  需[等待6秒]再重复执行当前任务  等待上份任务执行完毕后再等待6秒
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(execute(i, true), 3, 6, TimeUnit.SECONDS);
        }
    }

}
