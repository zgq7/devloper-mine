package com.loper.mine.core.juc.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * @author liaonanzhou
 * @date 2020-11-26 16:22
 * @description
 */
public class StartMain {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(10);

        TaskAction taskAction = new TaskAction(3);

        pool.invoke(taskAction);
    }
}
