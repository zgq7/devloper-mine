package com.loper.mine.rxjava;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liaonanzhou
 * @date 2021/9/7 11:36
 * @description
 **/
public class RxJavaTest {
    private final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    @Test
    public void demo() {
        // 被观察者
        Observable<String> observable = Observable
                .unsafeCreate(subscriber -> {
                    try {
                        subscriber.onNext("a");
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                });

        for (int i = 0; i < 10; i++) {

            // 观察者
            Observer<String> observer = new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("complete");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("error");
                }

                @Override
                public void onNext(String value) {
                    System.out.println(value);
                }
            };

            // 订阅/绑定
            observable.subscribe(observer);

        }

    }


    /**
     * 将一个 Integer 转化为 String 的一个函数式编程
     * <p>
     * 流式处理步骤
     **/
    @Test
    public void test1() {
        /*
         * 步骤：
         *
         * 输入 int值 -> 转化为String -> 正常 -> onNext -> onComplete
         *                             ↓
         *                           onError
         */
        Observable
                // 1：传入 int 值
                .unsafeCreate(
                        (Observable.OnSubscribe<Integer>) subscriber -> {
                            try {
                                subscriber.onNext(100);
                                subscriber.onCompleted();
                            } catch (Exception e) {
                                subscriber.onError(e);
                            }

                        })
                // 2：对传入 int 进行转换
                .map(String::valueOf)
                // 3：对第二部的处理过程进行监听
                .subscribe(new Subscriber<String>() {
                    // 第2步骤正常处理完成后该做的事..
                    @Override
                    public void onCompleted() {
                        System.out.println("complete");
                    }

                    //  第2步骤异常处理之后该做的事..
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error");
                    }

                    //  调用这个步骤之前先进行第2步骤
                    @Override
                    public void onNext(String value) {
                        System.out.println(value);
                    }
                });
    }

    /**
     * 测试 String 转 int 异常
     **/
    @Test
    public void test2() {
        Observable
                .unsafeCreate(
                        (Observable.OnSubscribe<String>) subscriber -> {
                            try {
                                subscriber.onNext("a");
                                subscriber.onCompleted();
                            } catch (Exception e) {
                                subscriber.onError(e);
                            }
                        })
                .map(Integer::valueOf)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error");
                    }

                    @Override
                    public void onNext(Integer value) {
                        System.out.println(value);
                    }
                });

    }

    /**
     * just 方法测试
     **/
    @Test
    public void just() {
        /*
         * 执行顺序：just -> doOnSubscribe -> subscribe
         */
        Observable
                .just(produceData());
    }

    /**
     * defer 方法测试
     * <p>
     * 基于订阅 的延迟处理机制
     **/
    @Test
    public void defer() {
        /*
         * 执行顺序：just -> doOnSubscribe -> subscribe
         */
        Observable
                .just(produceData())
                .doOnSubscribe(() -> System.out.println("subscribe"))
                .subscribe(s -> System.out.println("consume data"));
        /*
         * 执行顺序：doOnSubscribe -> defer(-> just) -> subscribe
         */
        Observable
                .defer(
                        () -> {
                            try {
                                return Observable.just(produceData());
                            } catch (Exception e) {
                                return Observable.error(e);
                            }
                        })
                .doOnSubscribe(
                        () -> System.out.println("subscribe")
                )
                .subscribe(s -> System.out.println("consume data"));

    }

    private String produceData() {
        System.out.println("produce data");
        return "producer";
    }


    /**
     * 重试机制
     **/
    @Test
    public void retry() throws InterruptedException {
        int cap = 3;
        CountDownLatch countDownLatch = new CountDownLatch(cap);
        Observable
                .unsafeCreate(
                        (Observable.OnSubscribe<String>) subscriber -> {
                            // 模拟一次远程调用（抛异常）
                            try {
                                subscriber.onNext(errorString());
                            } catch (Exception e) {
                                subscriber.onError(e);
                            }
                            subscriber.onCompleted();
                        })
                .retry(
                        (integer, throwable) -> {
                            if (integer > cap)
                                return false;

                            Observable.error(throwable);
                            countDownLatch.countDown();
                            System.out.println(integer);

                            return true;
                        }
                )
                .subscribe(
                        new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                System.out.println("complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("error");
                            }

                            @Override
                            public void onNext(String value) {
                                System.out.println(value);
                            }
                        }
                );

        countDownLatch.await();
    }

    @Test
    public void retryWhen() throws InterruptedException {
        AtomicLong retry = new AtomicLong(3);
        final long delay = 1;

        Observable
                .unsafeCreate(
                        (Observable.OnSubscribe<String>) subscriber -> {
                            // 模拟一次远程调用（抛异常）
                            try {
                                logger.info("模拟一次IO调用...");
                                subscriber.onNext(errorString());
                            } catch (Exception e) {
                                subscriber.onError(e);
                            }
                            subscriber.onCompleted();
                        })
                .retryWhen(observable ->
                        observable
                                .flatMap(
                                        (Func1<Throwable, Observable<?>>) throwable -> {
                                            if (retry.get() > 0) {
                                                logger.info("剩余重试次数：" + retry.decrementAndGet());
                                                return Observable.timer(delay, TimeUnit.SECONDS);
                                            }
                                            return Observable.error(throwable);
                                        }
                                )
                )
                // 指定观察者将在其上观察此 Observable 的调度者
                .observeOn(Schedulers.io())
                .subscribe(
                        new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                logger.info("complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                logger.info("error");
                            }

                            @Override
                            public void onNext(String value) {
                                logger.info(value);
                            }
                        }
                );

        while (retry.get() > 0) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private String errorString() {
        logger.info("模拟一次IO调用...");
        throw new RuntimeException();
    }


    /**
     * flatMap
     **/
    @Test
    public void flatMap() throws InterruptedException {
        int cap = 2;
        CountDownLatch countDownLatch = new CountDownLatch(cap);

        Observable
                .unsafeCreate(
                        (Observable.OnSubscribe<String>) subscriber -> {
                            /*
                             * onComplete 会导致整个调用链结束，因此循环 onNext 时需要将 onComplete 放在循环结束后
                             */
                            for (int i = 0; i < cap; i++) {
                                try {
                                    if (i == 0)
                                        subscriber.onNext("1");
                                    else
                                        subscriber.onNext("a");
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            }
                            subscriber.onCompleted();
                        }
                )
                .flatMap((Func1<String, Observable<Integer>>) s -> Observable.just(Integer.valueOf(s)))
                .subscribe(
                        new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                logger.info("complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                logger.info("error");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                logger.info("{}", integer);
                            }
                        });


        countDownLatch.await();
    }


}



































