package com.dev.juc.assemnbly;

/**
 * @author liaonanzhou
 * @date 2020-12-07 12:00
 * @description
 */
public class StartMain {

     public static void main(String[] args) {
        ProductOnlineBus pb = new ProductOnlineBus(99900);
        pb.starter();
    }
}
