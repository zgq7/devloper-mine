package com.dev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.*;

/**
 * Created on 2019-08-05 14:00.
 *
 * @author zgq7
 */
public class LocalThreadPool implements InitializingBean, DisposableBean {

    public static final String PACKAGE_BEAN_NAME = "localThreadPool";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * capacity 队列容量
     **/
    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5000);

    private final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

    private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
    private final ThreadFactory privilegedThreadFactory = Executors.privilegedThreadFactory();

    /**
     * corePoolSize             核心线程数量
     * maximumPoolSize          线程池允许最大线程池数量
     * keepAliveTime            当线程数超过本地线程核心数同时又小于设置的最大线程数，需要重新创建一个新线程时需要等待的时间
     * TimeUnit.MILLISECONDS    时间单位，这里我设置的是豪秒
     * workQueue                一个队列：当一个task被执行前进行使用
     * threadFactory            用于创建新线程的线程工厂
     * handler                  一个处理器：当线程被锁、或者队列的容量达到上限时 被调用
     **/
    public final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 3000, TimeUnit.MILLISECONDS
            , workQueue, defaultThreadFactory, handler
    );

    /**
     * 本地线程池被销毁后的回调方法
     **/
    @Override
    public void destroy() throws Exception {
        if (this.threadPoolExecutor == null)
            logger.info("指令->[本地线程池已销毁]");
    }

    /**
     * 本地线程池成功初始化的回调犯法
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.threadPoolExecutor != null)
            logger.info("指令->[本地线程池已成功初始化]");
    }

}
