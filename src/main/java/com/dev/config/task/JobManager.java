package com.dev.config.task;

import com.dev.utils.ReflectHelper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @apiNote 设定的时间间隔为3秒, 但job执行时间是5秒, 设置@DisallowConcurrentExecution
 * 以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 * @date 2020/4/8 18:39
 **/
public class JobManager implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
	private static final JobManager jobManager = new JobManager();
	private Scheduler scheduler;

	private JobManager() {
		try {
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
			logger.info("构建job任务管理器成功！");
		} catch (SchedulerException e) {
			logger.error("构建job任务管理器失败，堆栈：", e);
		}
	}

	public static JobManager getInstance() {
		return jobManager;
	}

	/**
	 * 添加所有的定时器任务
	 **/
	private void addJob() {
		ReflectHelper.getClassByInterface(TaskBuilder.class).forEach(clazz -> {
			// 是否同时继承了 Job 和 TaskBuilder
			try {
				if (TaskBuilder.class.isAssignableFrom(clazz)) {
					Object ins = clazz.newInstance();
					TaskBuilder builder = (TaskBuilder) ins;
					Job job = (Job) ins;
					// 定时器是否可构建
					if (builder.canBuilder()) {
						try {
							String taskName = builder.taskName();
							scheduler.scheduleJob(
									JobBuilder.newJob(job.getClass()).withIdentity(taskName, taskName).build(),
									TriggerBuilder.newTrigger().withIdentity(taskName, taskName)
											.withSchedule(
													CronScheduleBuilder.cronSchedule(builder.cronExpression())
											).build()
							);
							logger.info("构建定时器任务 {} 成功", taskName);
						} catch (SchedulerException e) {
							logger.error("添加job任务失败，堆栈：", e);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void start() throws SchedulerException {
		addJob();
		scheduler.start();
	}

	public void shutdown() throws SchedulerException {
		scheduler.shutdown(true);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("");
	}
}
