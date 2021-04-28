package com.loper.mine.core.juc.assemnbly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020-12-07 17:01
 * @description 生产总线
 */
public class ProductOnlineBus {

    private static final Logger logger = LoggerFactory.getLogger(ProductOnlineBus.class);
    /**
     * 生产奶酪数量
     **/
    private final int prodNum;
    /**
     * 牛奶=奶酪*2
     **/
    private final int milkMultiple = 2;
    /**
     * 发酵剂=奶酪*1
     **/
    private final int fjjMultiple = 1;
    /**
     * 奶酪仓库容量
     **/
    private final int cheeseCapacity = 1000;
    /**
     * 单词运输奶酪数量
     **/
    private final int truckCapacity = 100;
    /**
     * 总共需要运输多少次
     **/
    private final int needTruckTimes;
    /**
     * 生产线--阻塞队列
     **/
    private final BlockingDeque<MiikNode> milkNodeBlockingDeque;
    private final BlockingDeque<FJJNode> fjjNodeBlockingDeque;
    private final BlockingDeque<CheeseNode> cheeseNodeBlockingDeque;
    /**
     * 生产次数
     **/
    private final AtomicInteger trucked = new AtomicInteger(0);
    private final AtomicInteger milkProded = new AtomicInteger(0);
    private final AtomicInteger fjjProded = new AtomicInteger(0);
    /**
     * 实际运输次数
     **/
    private final AtomicInteger cheeseProded = new AtomicInteger(0);

    public ProductOnlineBus(int prodNum) {
        if ((prodNum % truckCapacity) != 0) {
            throw new RuntimeException("请输入truckCapacity的倍数");
        }
        this.prodNum = prodNum;
        this.milkNodeBlockingDeque = new LinkedBlockingDeque<>(milkMultiple);
        this.fjjNodeBlockingDeque = new LinkedBlockingDeque<>(fjjMultiple);
        this.cheeseNodeBlockingDeque = new LinkedBlockingDeque<>(cheeseCapacity);
        this.needTruckTimes = prodNum / truckCapacity;
    }

    public void starter() {
        new Thread(() -> {
            int len = prodNum * milkMultiple;
            for (int i = 0; i < len; i++) {
                try {
                    milkNodeBlockingDeque.put(new MiikNode(i));
                    milkProded.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "MilkThread").start();

        new Thread(() -> {
            int len = prodNum * fjjMultiple;
            for (int i = 0; i < len; i++) {
                try {
                    fjjNodeBlockingDeque.put(new FJJNode(i));
                    fjjProded.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "FJJThread").start();

        new Thread(() -> {
            for (int i = 0; i < prodNum; i++) {
                try {
                    for (int j = 0; j < milkMultiple; j++) {
                        milkNodeBlockingDeque.take();
                    }
                    for (int j = 0; j < fjjMultiple; j++) {
                        fjjNodeBlockingDeque.take();
                    }
                    cheeseNodeBlockingDeque.put(new CheeseNode(i));
                    cheeseProded.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CheeseThread").start();

        new Thread(() -> {
            while (trucked.get() < needTruckTimes) {
                try {
                    for (int i = 0; i < truckCapacity; i++) {
                        cheeseNodeBlockingDeque.take();
                    }
                    trucked.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("over of->cheese:[{}],milk:[{}],fjj[{}],truck:[{}]",
                    cheeseProded.get(), milkProded.get(), fjjProded.get(), trucked.get());
        }, "TruckThread").start();
    }

    /**
     * 牛奶
     **/
    private class MiikNode {
        public MiikNode(int seq) {
            logger.info("生产牛奶[{}]...", seq);
        }
    }

    /**
     * 发酵剂
     **/
    private class FJJNode {
        public FJJNode(int seq) {
            logger.info("生产发酵剂[{}]...", seq);
        }
    }

    /**
     * 奶酪
     **/
    private class CheeseNode {
        public CheeseNode(int seq) {
            logger.info("生产奶酪[{}]...", seq);
        }
    }

}
