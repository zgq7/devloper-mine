package com.dev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created on 2019-08-05 14:00.
 *
 * @author zgq7
 */
public class LocalThreadPool implements InitializingBean, DisposableBean {

	public static final String PACKAGE_BEAN_NAME = "localThreadPool";

	private static final Logger logger = LoggerFactory.getLogger(LocalThreadPool.class);
	//=====================================================获取数据池实例-单例模式
	private static LocalThreadPool localThreadPool = null;
	/**
	 * capacity 队列容量
	 **/
	private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5000);
	private final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
	/**
	 * thread 工厂
	 **/
	private final ThreadFactory privilegedThreadFactory = Executors.privilegedThreadFactory();
	private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
	/**
	 * 1：corePoolSize             核心线程数量
	 * 2：maximumPoolSize          线程池允许最大线程池数量
	 * 3：keepAliveTime            当线程数超过本地线程核心数同时又小于设置的最大线程数，需要重新创建一个新线程时需要等待的时间
	 * 4：TimeUnit.MILLISECONDS    时间单位，这里我设置的是豪秒
	 * 5：workQueue                一个队列：当一个task被执行前进行保存该task，用来保存等待的线程
	 * 6：threadFactory            用于创建新线程的线程工厂
	 * 7：handler                  一个处理器：当线程被锁、或者队列的容量达到上限时 被调用，告知调用方
	 **/
	public final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
			10,
			300,
			TimeUnit.SECONDS,
			workQueue,
			defaultThreadFactory,
			handler);

	private LocalThreadPool() {
	}

	/**
	 * 获取实例
	 **/
	public static LocalThreadPool getInstance() {
		synchronized (LocalThreadPool.class) {
			if (localThreadPool == null) {
				localThreadPool = new LocalThreadPool();
				return localThreadPool;
			}
		}
		return localThreadPool;
	}

	/**
	 * 使用本地线程池执行任务
	 **/
	public void execute(final Runnable runnable) {
		execute(null, runnable, null);
	}

	/**
	 * 使用本地线程池执行任务
	 **/
	public void execute(final String name, final Runnable runnable) {
		execute(name, runnable, null);
	}

	/**
	 * @param name             自定义线程名称
	 * @param runnable         任务线程
	 * @param exceptionHandler 线程内部异常处理器
	 *                         使用本地线程池执行任务
	 **/
	@Scheduled
	public void execute(final String name, final Runnable runnable, final Thread.UncaughtExceptionHandler exceptionHandler) {
		threadPoolExecutor.execute(() -> {
			final Thread currentThread = Thread.currentThread();
			if (Objects.nonNull(name)) {
				currentThread.setName(name);
			}
			if (Objects.isNull(exceptionHandler)) {
				//默认配置异常处理器，打印正常日志即可
				currentThread.setUncaughtExceptionHandler((thread, throwable) -> {
					logger.error("线程->{}执行异常->{}", thread.getName(), throwable);
				});
			} else {
				currentThread.setUncaughtExceptionHandler(exceptionHandler);
			}
			runnable.run();
		});
	}

	/**
	 * 执行带返回的任务，但是内部的异常不会被捕获，当调用Future.get()的时候异常会被封装在ExecutionException当中被抛出
	 **/
	public <T> Future<T> submit(Callable<T> callable) {
		return threadPoolExecutor.submit(callable);
	}

	/**
	 * 本地线程池被销毁后的回调方法
	 **/
	@Override
	public void destroy() throws Exception {
		logger.info("指令->[本地线程池已销毁]");
		threadPoolExecutor.shutdown();
		//等待所有线程池的任务完成，最多等待三分钟
		threadPoolExecutor.awaitTermination(3, TimeUnit.MINUTES);
		//MoreExecutors.shutdownAndAwaitTermination(threadPoolExecutor, 1, TimeUnit.SECONDS);
	}

	/**
	 * 本地线程池成功初始化的回调犯法
	 **/
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("指令->[本地线程池已成功初始化]");
	}

}
