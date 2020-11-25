package com.dev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020/9/14 9:56
 * @description
 **/
public class CustomerThreadPoolManager {

    private static final Logger logger = LoggerFactory.getLogger(CustomerThreadPoolManager.class);

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
            10,
            300,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public static void execute(Runnable runnable, AtomicInteger retrytimes) {
        if (retrytimes.get() > 0) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, threadPoolExecutor);
            future.exceptionally(e -> {
                e.printStackTrace();
                retrytimes.decrementAndGet();
                execute(runnable, retrytimes);
                return null;
            });
        }
    }

    public static void execute(Runnable runnable) {
        execute(runnable, new AtomicInteger(3));
    }


    public static void main(String[] args) {
        execute(() -> {
            logger.info("junit");
            throw new RuntimeException();
        });

    }

}
