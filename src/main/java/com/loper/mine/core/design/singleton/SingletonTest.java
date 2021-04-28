package com.loper.mine.core.design.singleton;

/**
 * @author zgq7
 * @date 2020-08-04 11:08
 * @description 单利模式
 **/
public class SingletonTest {

    private static volatile SingletonTest instance = null;

    private SingletonTest() {
        System.out.println(Thread.currentThread().getName() + "请求单例");
    }

    //方式1:直接加synchronized 修饰方法
    // 缺点：太重量级，高并发模式下不适用
    /*public static synchronized SingletonTest getInstance() {
        if (instance == null) {
            instance = new SingletonTest();
        }
        return instance;
    }*/
    //方式2:DCL模式（双端检锁机制），instance 为空时锁代码块，进入锁代码块后再判断instance是否为空
    //缺点1：syn 无法禁止指令重排（不存在数据依赖性则可能重排），syn 第一次检测为空时，instance 对象可能还没有完成初始化，下一个线程拿到的instance 依然会存在为空的情况
    //解决方法：instance 使用 volatile 修饰，禁止指令重排，保证instance 初始化完成后其他线程再去判断 instance 是否为空
    public static SingletonTest getInstance() {
        if (instance == null) {
            synchronized (SingletonTest.class) {
                if (instance == null) {
                    instance = new SingletonTest();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //System.out.println(Thread.currentThread().getName() + "获取单例：" + getInstance());
            new Thread(SingletonTest::getInstance, String.valueOf(i)).start();
        }


    }


}
