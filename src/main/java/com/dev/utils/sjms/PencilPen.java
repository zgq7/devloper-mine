package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 16:51.
 * <p>
 * 铅笔
 * 1:可写在纸上、玻璃上
 * 2：都可擦除
 *
 * @author zgq7
 */
public class PencilPen extends Pen {

    /**
     * 耐久度
     **/
    private int writableLength;

    /**
     * 是否可写
     **/
    private boolean canBeWrite;

    /**
     * 是否可擦
     **/
    private boolean canBeClean;

    public PencilPen() {
        this.writableLength = 1000;
        this.canBeWrite = true;
        this.canBeClean = true;
    }

    public int getWritableLength() {
        return writableLength;
    }

    @Override
    public boolean canBeWrite() {
        return this.canBeWrite && this.writableLength > 0;
    }

    @Override
    public boolean canBeClean() {
        return this.canBeClean;
    }

    @Override
    public void writeOn(Carrier carrier) {
        if (this.writableLength == 0) {
            PenFactory.recyclePen(this);
        }
        this.writableLength = writableLength - carrier.consume(this);
    }
}
