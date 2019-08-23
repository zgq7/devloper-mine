package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 16:53.
 * <p>
 * 钢笔
 * 1:可写在纸上，不可写在玻璃上
 * 2：纸不可擦除、玻璃可擦除
 *
 * @author zgq7
 */
public class GonPen extends Pen {

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

    public GonPen() {
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
        if (carrier instanceof GlassCarrier) {
            this.writableLength = writableLength - carrier.consume(this);
            this.canBeWrite = false;
            this.canBeWrite = false;
        }
        if (carrier instanceof PaperCarrier) {
            this.canBeWrite = true;
            this.canBeClean = false;
        }
    }


}
