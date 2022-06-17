package com.loper.mine.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author liaonanzhou
 * @date 2021/11/17 17:15
 * @description
 **/
public class LoggerIdUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerIdUtilTest.class);

    private final static ExecutorService EXECUTOR = new ThreadPoolExecutor(5,
            10,
            3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50));


    @Test
    public void test() throws InterruptedException {
        LoggerIdUtil.random();
        logger.info("主线程");
        int cap = 10;
        CountDownLatch countDownLatch = new CountDownLatch(cap);

        final String traceId = LoggerIdUtil.getCurrentThreadTraceId();
        for (int i = 0; i < cap; i++) {
            EXECUTOR.execute(() -> {
                //TraceIdUtil.setTraceId(traceId);
                logger.info("子线程");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        EXECUTOR.shutdown();
    }
}