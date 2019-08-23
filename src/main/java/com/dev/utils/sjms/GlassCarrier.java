package com.dev.utils.sjms;

/**
 * Created on 2019-08-23 17:01.
 *
 * @author zgq7
 */
public class GlassCarrier extends Carrier {

    @Override
    public int consume(Pen pen) {
        if (pen instanceof PencilPen) {
            return 1;
        } else if (pen instanceof GonPen) {
            return 3;
        } else {
            return 5;
        }
    }

}
