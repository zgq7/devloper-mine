package com.loper.mine.core.juc;

import com.loper.mine.core.ExceptionSuppiler;
import com.loper.mine.core.VoidSupplier;
import com.loper.mine.utils.exception.ServiceException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020/9/14 9:56
 * @description 线程重试机制统一处理管理器
 **/
public class ThreadRetryManager {

    private static final Logger logger = LoggerFactory.getLogger(ThreadRetryManager.class);

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
            10,
            300,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000),
            new ThreadFactoryBuilder().setNameFormat("retry-thread-%d").build(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 任务重试主API
     *
     * @param retrytimes        重试次数
     * @param runnable          task that will be execcute
     * @param retryOutSupplier  重试完所有次数后处理回调...
     * @param exceptionSupplier 重试期间报错处理处理回调...
     */
    public void execute(AtomicInteger retrytimes,
                        Runnable runnable,
                        VoidSupplier retryOutSupplier,
                        ExceptionSuppiler exceptionSupplier) {

        if (retrytimes.get() > 0) {

            CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, threadPoolExecutor);

            future.exceptionally(throwable -> {
                if (exceptionSupplier != null) {
                    exceptionSupplier.supplier(throwable);
                } else {
                    logger.error("重试线程报错：", throwable);
                }

                retrytimes.decrementAndGet();
                execute(retrytimes, runnable, retryOutSupplier, exceptionSupplier);
                return null;
            });
        } else if (retryOutSupplier != null) {
            retryOutSupplier.supplier();
        } else {
            logger.error("重试失败，不再重试！");
        }
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadRetryManager manager = new ThreadRetryManager();
        manager.execute(new AtomicInteger(3),
                () -> {
                    throw new ServiceException(4000, "token过期");
                }, () -> {
                    logger.error("777");
                }, (e) -> {
                    if (e.getCause() instanceof ServiceException) {
                        ServiceException serviceException = (ServiceException) e.getCause();
                        if (serviceException.getCode() == 4000) {
                            logger.info("重新获取token...");
                        }
                    }
                });

        TimeUnit.SECONDS.sleep(3);
        manager.shutdown();
    }


}
