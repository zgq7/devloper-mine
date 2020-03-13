package com.dev.utils.dxc;

import java.util.concurrent.Callable;

/**
 * Created on 2019-09-23 23:35.
 *
 * @author zgq7
 */
public class Dxc1 implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        System.out.println("hello");
        return true;
    }
}
