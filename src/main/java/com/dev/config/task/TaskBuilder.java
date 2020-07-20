package com.dev.config.task;

/**
 * @author Leethea_廖南洲
 * @version 1.0  所有的
 * @date 2020/4/9 9:30
 **/
public interface TaskBuilder {

	/**
	 * 是否能够构建
	 **/
	boolean canBuilder();

	/**
	 * cron 表达式
	 **/
	String cronExpression();

	/**
	 * 任务名称
	 **/
	String taskName();

}
