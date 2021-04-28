package com.loper.mine.core.juc.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2020-11-30 14:44
 * @description
 */
public class TaskAction extends RecursiveAction {

    private static final Logger logger = LoggerFactory.getLogger(TaskAction.class);

    /**
     * 总共任务量
     **/
    private final int taskCount;
    /**
     * 当前task被分配的任务量
     **/
    private final int taskMete;
    /**
     * 当前task序号
     **/
    private int taskRank;
    /**
     * 每个task最大可处理任务量
     **/
    private final int maxTask = 1;

    public TaskAction(int taskCount) {
        this.taskCount = taskCount;
        this.taskMete = taskCount;
    }

    private TaskAction(int taskCount, int taskMete, int taskRank) {
        this.taskCount = taskCount;
        this.taskMete = taskMete;
        this.taskRank = taskRank;
    }

    @Override
    protected void compute() {
        // 任务分配量是否满足处理条件，不满足则将任务再拆分
        if (taskMete == maxTask) {
            printSelf();
        } else {
            List<TaskAction> taskActionList = new ArrayList<>();
            for (int i = 1; i <= taskCount; i++) {
                taskActionList.add(new TaskAction(taskCount, 1, i));
            }
            // 执行所有任务
            invokeAll(taskActionList);
        }
    }

    /**
     * task 1 正常结束 ->
     * task 2 执行报错 ->
     * task 3 直接终止
     **/
    private void printSelf() {
        logger.info("SON TASK RANK [{}] START", taskRank);
        try {
            TimeUnit.SECONDS.sleep(taskRank * 3);
            if (taskRank == 2) {
                logger.error("eroor occured");
                throw new RuntimeException("error");
            } else {
                logger.info("TASK [{}] OVER", taskRank);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
