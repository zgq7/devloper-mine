package com.dev.utils.sjms;

/**
 * Created on 2019-08-26 10:01.
 * 载体工厂
 *
 * @author zgq7
 */
public class CarrierFactory {

    public static Carrier newPaperCarrier() {
        return new PaperCarrier();
    }

    public static Carrier newGlassCarrier() {
        return new GlassCarrier();
    }

    /**
     * 回收机制本来想用观察者模式写的
     **/
    public static void recycle(Carrier carrier) {
        carrier = null;
        System.gc();
    }
}
