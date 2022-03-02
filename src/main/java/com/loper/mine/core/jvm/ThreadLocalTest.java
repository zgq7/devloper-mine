package com.loper.mine.core.jvm;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest {

//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        threadLocal.set("1");
//
//        int cap = 1000000;
//        for (int i = 0; i < cap; i++) {
//            map.put("dsddsasaddddddddddddddddd" + i, "2133333333333333333333333" + i);
//            System.out.println("i=" + i + ",threadlocal = " + threadLocal.get() + " heap total = " + Runtime.getRuntime().totalMemory() + ",heap free = " + Runtime.getRuntime().freeMemory());
//            System.gc();
//        }
//    }


    public static void main(String[] args) {
        int cap = 1000000;
//        for (int i = 0; i < cap; i++) {
//            ThreadLocal<String> threadLocal = new ThreadLocal<>();
//            threadLocal.set(String.valueOf(i));
//            System.out.println("i=" + i + " heap total = " + Runtime.getRuntime().totalMemory() + ",heap free = " + Runtime.getRuntime().freeMemory());
//            if (i == (cap -1))
//                System.out.println(1);
//        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < cap; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
            System.out.println("i=" + i + " heap total = " + Runtime.getRuntime().totalMemory() + ",heap free = " + Runtime.getRuntime().freeMemory());
        }
    }

}
