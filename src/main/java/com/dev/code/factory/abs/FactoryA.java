package com.dev.code.factory.abs;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 **/
public class FactoryA implements Factory {
    @Override
    public Tv tv() {
        return new LeTv();
    }

    @Override
    public Location location() {
        return new Japan();
    }
}
