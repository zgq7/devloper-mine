package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 16:45.
 *
 * @author zgq7
 */
public abstract class Pen {

    /**
     * 是否可写
     **/
    public abstract boolean canBeWrite();

    /**
     * 是否可擦
     **/
    public abstract boolean canBeClean();

    public abstract void writeOn(Carrier carrier);

}
