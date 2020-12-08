package com.dev.juc.broadcast;

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
public class SonTask extends RecursiveAction {

    private static final Logger logger = LoggerFactory.getLogger(SonTask.class);

    //总共任务量
    private int taskCount;
    //分配的任务量
    private int taskMete;
    //任务排名
    private int taskRank;
    //最大可处理任务量
    private final int MAX_RANK = 1;

    public SonTask(int taskCount) {
        this.taskCount = taskCount;
        this.taskMete = taskCount;
    }

    private SonTask(int taskCount, int taskMete, int taskRank) {
        this.taskCount = taskCount;
        this.taskMete = taskMete;
        this.taskRank = taskRank;
    }

    @Override
    protected void compute() {
        if (taskMete == MAX_RANK) {
            printSelf();
        } else {
            List<SonTask> sonTaskList = new ArrayList<>();
            for (int i = 1; i <= taskCount; i++) {
                sonTaskList.add(new SonTask(taskCount, 1, i));
            }
            invokeAll(sonTaskList);
        }
    }

    private void printSelf() {
        logger.info("son task rank -> [{}]", taskRank);
        try {
            TimeUnit.SECONDS.sleep(taskRank * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (taskRank == 2) {
            throw new RuntimeException("error");
        }
        logger.info("TASK [{}] OVER", taskRank);
    }

}
