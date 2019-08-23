package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 17:00.
 * <p>
 * 载体
 *
 * @author zgq7
 */
public abstract class Carrier {

    /**
     * 每次的消耗  架设
     *        玻璃        纸
     * 铅笔     1         1
     * 钢笔     3         2
     * 马克笔   5         3
     *
     **/
    public abstract int consume(Pen pen);

}
