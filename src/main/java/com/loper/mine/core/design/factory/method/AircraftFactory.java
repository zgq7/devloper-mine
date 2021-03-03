package com.loper.mine.core.design.factory.method;

/**
 * @author zgq7
 * @date 2020-08-11 17:08
 **/
public class AircraftFactory implements Factory {
    @Override
    public Product product() {
        return new Aircraft();
    }
}
