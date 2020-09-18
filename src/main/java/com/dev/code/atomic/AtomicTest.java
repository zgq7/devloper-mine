package com.dev.code.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zgq7
 * @date 2020-08-05 21:08
 **/
public class AtomicTest {
    private static volatile AtomicInteger tid = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        BlockThread blockThread = new BlockThread();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executorService.submit(() -> blockThread.tryFunction(finalI));
        }
        executorService.shutdown();
    }

    static class BlockThread {
        private volatile int count = 0;

        void tryFunction(int tag) {
            while (true) {
                if (tag == tid.get()) {
                    int cur = 0;
                    for (; cur < 10; cur++) {
                        count++;
                        if (0 == count % 2) {
                            System.out.println(String.format("Thread %d Get Slice count:%d,cur:%d", tag, count, cur));
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tid.incrementAndGet();
                    break;
                }
            }
        }
    }


}
