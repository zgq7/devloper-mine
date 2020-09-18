package com.dev.code.factory.abs;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 **/
public class FactoryB implements Factory {
    @Override
    public Tv tv() {
        return new MediaTv();
    }

    @Override
    public Location location() {
        return new China();
    }
}
