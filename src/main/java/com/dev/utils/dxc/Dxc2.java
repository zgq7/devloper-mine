package com.dev.utils.dxc;

import java.util.concurrent.Callable;

/**
 * Created on 2019-09-23 23:35.
 *
 * @author zgq7
 */
public class Dxc2 implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        synchronized (this) {
            Thread.sleep(3000);
            System.out.println("hi");
            return true;
        }
    }
}
