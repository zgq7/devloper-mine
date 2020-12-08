package com.dev.juc.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

/**
 * @author liaonanzhou
 * @date 2020-11-26 16:22
 * @description
 */
public class StartMain {

    private static final Logger logger = LoggerFactory.getLogger(StartMain.class);

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(10);

        SonTask sonTask = new SonTask(3);

        pool.invoke(sonTask);

        //TimeUnit.SECONDS.sleep(10);
    }
}
