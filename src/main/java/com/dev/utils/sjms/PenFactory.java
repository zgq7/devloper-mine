package com.dev.utils.sjms;

/**
 * Created on 2019-08-26 9:49.
 * 笔工厂
 *
 * @author zgq7
 */
public class PenFactory{

    public static PencilPen newPencilPen() {
        return new PencilPen();
    }

    public static GonPen newGonPen() {
        return new GonPen();
    }

    public static MarkPen newMarkPen() {
        return new MarkPen();
    }

    public static void recyclePen(Pen pen) {
        pen = null;
        System.gc();
    }
}
