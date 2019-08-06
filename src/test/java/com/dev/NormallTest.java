package com.dev;

import com.dev.config.LocalThreadPool;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019-07-30 10:59.
 *
 * @author zgq7
 */
public class NormallTest {

    /**
     * TimeUtils 类的功能测试
     **/
    @Test
    public void test01() throws InterruptedException {
        //System.out.println(String.format("asst:captcha:%s", "2222"));
        //System.out.println(RandomStringUtils.random(6));
        int[] i = new int[]{0};
/*        Thread thread1 = new Thread(() -> {
            synchronized (this) {
                System.out.println("thread1->" + i[0]);
                i[0]++;
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (this) {
                System.out.println("thread2->" + i[0]);
                i[0]++;
            }
        });*/

        Thread thread3 = new Thread(() -> {
            System.out.println("thread3->" + i[0]);
            i[0]++;
        });

        thread3.start();
        //thread1.start();
        //thread2.start();

        //thread1.run();
        //thread2.run();

    }
}
