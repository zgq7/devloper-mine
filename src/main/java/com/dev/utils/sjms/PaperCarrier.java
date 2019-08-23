package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 17:02.
 *
 * @author zgq7
 */
public class PaperCarrier extends Carrier{


    @Override
    public int consume(Pen pen) {
        if (pen instanceof PencilPen) {
            return 1;
        } else if (pen instanceof GonPen) {
            return 2;
        } else {
            return 3;
        }
    }
}
