package com.loper.mine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.loper.mine.config.LocalThreadPool;
import com.loper.mine.controller.dto.LoginDto;
import com.loper.mine.model.Aopi;
import com.loper.mine.utils.JSRValidatorUtil;
import com.loper.mine.utils.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * Created on 2019-07-30 10:59.
 *
 * @author zgq7
 */
public class NormallTest {

    /**
     * TimeUtils 类的功能测试
     **/
    @Test
    public void test01() throws Exception {
        //System.out.println(String.format("asst:captcha:%s", "2222"));
        //System.out.println(RandomStringUtils.random(6));
        System.out.println(TimeUtils.timeStampTotTimeMills(1577808000000L));
    }

    @Test
    public void test02() {
        Thread thread1 = new Thread(() -> {
            LocalThreadPool localThreadPool1 = LocalThreadPool.getInstance();
            System.out.println(localThreadPool1.hashCode());
        });
        Thread thread2 = new Thread(() -> {
            LocalThreadPool localThreadPool2 = LocalThreadPool.getInstance();
            System.out.println(localThreadPool2.hashCode());
        });

        thread1.start();
        thread2.start();

        try {
            synchronized (Thread.currentThread()) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() {
        // 针对二进制，转换成二进制后向左移动n位，后面用0补齐
        System.out.println(1 << 0);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println("=================================");
        // 针对二进制，只要有一个余数为0，就为0
        System.out.println(1 & 5);
        System.out.println(5 & 1);
        System.out.println(1 & 4);
        System.out.println(4 & 1);
        System.out.println("=================================");
        System.out.println(5 & ~5);
        System.out.println(5 & ~4);
        System.out.println("=================================");
        System.out.println(5 | 4);
        System.out.println(1 | 8);
    }


    /**
     * 冒泡排序 降序
     **/
    @Test
    public void test05() {
        int[] test = new int[]{5, 8, 4, 1, 6, 3, 7, 9, 10, 2};

        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                if (test[i] < test[j]) {
                    int k = test[j];
                    test[j] = test[i];
                    test[i] = k;
                }
            }
        }
        for (int p = 0; p < test.length; p++) {
            System.out.print(test[p] + ",");
        }
    }

    /**
     * 快速排序
     **/
    @Test
    public void quickSortTest() {
        int[] test = new int[]{5, 8, 4, 1, 6, 3, 7, 9, 10, 11};
        quickSort(test, 0, 8);
        for (int i : test) {
            System.out.print(i + ",");
        }
        int index = erFenSearch(test, 100);
        System.out.println(index);
    }

    /**
     * 快速排序
     **/
    private void quickSort(int[] test, int low, int high) {
        if (low > high)
            return;

        int temp = test[low], //设置基数
                i = low, j = high;

        while (i < j) {
            while (temp < test[j] && i < j) {
                j--;
            }
            while (temp >= test[i] && i < j) {
                i++;
            }
            if (i < j) {
                int k = test[j];
                test[j] = test[i];
                test[i] = k;
            }
        }
        test[low] = test[i];
        test[i] = temp;
        quickSort(test, low, j - 1);
        quickSort(test, j + 1, high);
    }

    /**
     * 二分查找
     */
    private int erFenSearch(int[] arr, int data) {
        // 起始位
        int low = 0;
        // 结束位
        int high = arr.length - 1;
        // 中位
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == data) {
                return mid;
            } else if (arr[mid] < data) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * localthread
     **/
    @Test
    public void test07() throws InterruptedException {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        ThreadLocal<String> threadLocal1 = new ThreadLocal<>();

        Thread thread1 = new Thread(() -> {
            threadLocal.set(1);
            threadLocal1.set("sb");
            System.out.println("1：" + threadLocal.get());
            System.out.println("1：" + threadLocal1.get());
        });
        Thread thread2 = new Thread(() -> {
            threadLocal.set(2);
            System.out.println("2：" + threadLocal.get());
        });

        thread1.start();
        thread2.start();

        Thread.sleep(1000);
    }

    /**
     * CyclicBarrier测试
     **/
    @Test
    public void test09() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("全部到达，屏障打开了！！！");
        });
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "到达！");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Semaphore
     **/
    @Test
    public void test10() {
        Semaphore semaphore = new Semaphore(3);//三个资源，公平锁

        //6线程抢占三个资源
        int p = 0;
        for (int i = 1; i < 7; i++) {
            new Thread(() -> {
                System.out.println("");
                try {
                    semaphore.acquire();//抢占资源
                    System.out.println(Thread.currentThread().getName() + "抢到资源");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "离开当前持有的资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();//释放资源
                }
            }, String.valueOf(i)).start();

            p++;
        }

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 闭锁
     **/
    @Test
    public void test12() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getId());
                } catch (Exception ignore) {

                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
    }

    @Test
    public void doubleThread() {
        LocalDate now = LocalDate.now();
        String e = String.valueOf(LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth()));
        String s = String.valueOf(LocalDate.of(now.getYear() - 3, 1, 1));
        System.out.println(e);
        System.out.println(s);
    }

    @Test
    public void test11() {
        Date date = new Date();
        System.out.println(date);
        System.out.println(JSON.toJSONString(date));
        System.out.println(JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONStringWithDateFormat(date, null, SerializerFeature.WriteDateUseDateFormat));
    }

    @Test
    public void Test12() {
        boolean[] s = new boolean[]{false, false};
        System.out.println(Arrays.asList(s));
        Boolean[] t = new Boolean[]{false, false};
        System.out.println(Arrays.asList(t));
    }

    @Test
    public void Test13() {
        List<Map<Integer, Integer>> mapList = new ArrayList<>();
        Map<Integer, Integer> keyMap = new HashMap<>();
        keyMap.put(1, 1);
        keyMap.put(4, 1);
        Map<Integer, Integer> keyMap1 = new HashMap<>();
        keyMap1.put(1, 17);
        keyMap1.put(4, 2);
        Map<Integer, Integer> keyMap2 = new HashMap<>();
        keyMap2.put(1, 33);
        keyMap2.put(4, 3);
        Map<Integer, Integer> keyMap3 = new HashMap<>();
        keyMap3.put(1, 42);
        keyMap3.put(4, 4);

        mapList.add(keyMap);
        mapList.add(keyMap1);
        mapList.add(keyMap2);
        mapList.add(keyMap3);

        System.out.println(
                mapList.stream().collect(Collectors.toMap(
                        item -> {
                            System.out.println(item.get(4));
                            return item.get(1);
                        },
                        item -> item.get(4)))
        );

        System.out.println(
                mapList.stream().sorted((o1, o2) -> {
                    if (o1.get(1).equals(o2.get(1)))
                        return 0;
                    return o1.get(1) > o2.get(1) ? 1 : -1;
                }).collect(Collectors.toList()).stream().collect(Collectors.toMap(
                        item -> {
                            System.out.println(item.get(1));
                            return item.get(1);
                        },
                        item -> item.get(4)))
        );


        Map<Integer, Integer> keyMap4 = new HashMap<>();
        keyMap4.put(1, 49);
        keyMap4.put(2, 4);
        keyMap4.put(3, 192);
        System.out.println(keyMap4);

    }

    /**
     * @author Leethea
     * @Description HashMap 中 hash 值的計算方式
     * 当 integer 的值 处于 -128-127 之间时，有序
     * @Date 2020/1/10 9:26
     **/
    @Test
    public void Test14() {
        Object key = 10000;

        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        int index = (16 - 1) & hash;

        System.out.println("hash = " + hash);

        System.out.println("index = " + index);

        for (int bi = 0; ; ++bi) {
            System.out.println(bi);
        }
    }

    @Test
    public void Test15() {
        System.out.println(LocalDateTime.now().getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 2, 9).getDayOfWeek().getValue());
    }

    @Test
    public void test16() {
        Optional<String> o1 = Optional.of("123");
        System.out.println(o1.orElse("321"));
        System.out.println(o1.orElseGet(() -> "321"));

        //orElseGet 性能 优于orElse
        Optional<String> o2 = Optional.empty();
        System.out.println(o2.orElse("321"));
        System.out.println(o2.orElseGet(() -> "321"));

    }

    @Test
    public void test17() {
        System.out.println("=========" + t1());
    }

    private Integer t1() {
        try {
            System.out.println(1);
            return 1;
        } finally {
            System.out.println(11);
            return 11;
        }
    }

    @Test
    public void test18() {
        Integer s = null;
        Optional<Integer> optional = Optional.ofNullable(s);
        //第一种
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println(123);
        }
        //第二种
        optional.ifPresent(System.out::println);
        //第三种
        System.out.println(optional.orElse(123));
        //第四种
        System.out.println(optional.orElseGet(() -> 123));
    }

    @Test
    public void test19() {
        Aopi aopi = new Aopi("123", 23);
        System.out.println(JSON.toJSONString(aopi));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void test20() {
        Date date = new Date();
        LocalDate localDate = LocalDate.now();

        System.out.println(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue());
        System.out.println(localDate.getDayOfWeek().getValue());
    }

    @Test
    public void test21() {
        List<Integer> ass = new ArrayList<>();
        ass.add(8);
        ass.add(3);
        ass.add(7);
        ass.add(2);
        List<Integer> ass2 = new ArrayList<>();
        ass2.add(5);
        ass2.add(6);

        ass.sort(Comparator.comparing(Integer::intValue));
        System.out.println(ass);
        for (Integer i : ass2) {
            List<Integer> bit = new ArrayList<>();
            boolean t = false;
            for (Integer d : ass) {
                if (d > i && !t) {
                    bit.add(i);
                    t = true;
                }
                bit.add(d);
            }
            ass = bit;
        }
        System.out.println(ass);
    }

    /**
     * IP 测试
     **/
    @Test
    public void test22() throws UnknownHostException {
        InetAddress address1 = Inet4Address.getLocalHost();
        InetAddress address2 = Inet6Address.getLocalHost();

        System.out.println(address1);
        System.out.println(address2);

    }

    @Test
    public void test23() {
        String s = "1,3,5";
        StringBuilder sb = new StringBuilder();
        Arrays.asList(StringUtils.split(s, ",")).forEach(t -> {
            sb.append(t)
                    .append(",");
        });
        System.out.println(sb);
        System.out.println(sb.length());
        System.out.println(sb.substring(0, s.length()));
    }

    @Test
    public void test24() {
        final LocalDate now = LocalDate.now();
        final LocalDate birth = LocalDate.of(2017, 1, 1);
        Period p = Period.between(birth, now);
        long months = p.getYears() * 12 + p.getMonths();
        if (p.getDays() > 0) {
            months++;
        }
        System.out.println(months);
    }

    @Test
    public void test25() {
        Date date1 = new Date(2020, 1, 1, 14, 20, 10);
        Date date2 = new Date(2020, 1, 1, 15, 20, 11);
        long hour = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60);
        long hour1 = (date2.getTime() - date1.getTime()) % (1000 * 60 * 60);
        System.out.println(hour);
        System.out.println(hour1);
    }

    @Test
    public void test26() {
        Date date = new Date();
        Date date2 = Date.from(LocalDate.of(2020, 11, 7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
        System.out.println(simpleDateFormat.format(date));
        System.out.println(simpleDateFormat.format(date2));
    }

    @Test
    public void test28() {
        System.out.println(3 & 9);

        ConcurrentHashMap<String, String> crp = new ConcurrentHashMap<>();
        crp.put("a", "acv");

        LinkedHashMap<String, String> lkp = new LinkedHashMap<>();
        lkp.put("lkp", "lkp");

        Collections.synchronizedList(new ArrayList<>());

    }

    @Test
    public void test29() throws IOException {
        int[] it = new int[]{4, 1, 2, 1, 2};

        Map<Integer, Integer> map = new HashMap<>();
        for (int value : it) {
            if (!map.containsKey(value)) {
                map.put(value, 1);
            } else {
                map.put(value, map.get(value) + 1);
            }
        }

        List<Integer> ls = new ArrayList<>();
        for (Map.Entry e : map.entrySet()) {
            if (e.getValue().equals(1)) {
                ls.add((Integer) e.getKey());
            }
        }

        System.out.println(ls);

    }

    @Test
    public void Test30() throws IOException {
        new BufferedWriter(new FileWriter("a.txt"));
        new BufferedReader(new Reader() {
            @Override
            public int read(@NotNull char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        });
        //new BufferedReader(new FileInputStream("a.dat"));
        new GZIPOutputStream(new FileOutputStream("a.zip"));
        new ObjectInputStream(new FileInputStream("a.dat"));

    }

    @Test
    public void test31() {
        int n = 1;
        for (int i = 1; i < 10; i++) {
            n = (n + 1) * 2;
            System.out.println(n);
        }


    }

    @Test
    public void test32() {
        try {
            throw new NullPointerException("123");
        } catch (Exception e) {
            System.out.println("error");
        } finally {
            System.out.println("OK");
        }
    }

    @Test
    public void test33() {
        List<Integer> abs = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        for (int i = 0; i < abs.size(); i++) {
            if (abs.get(i).equals(4))
                abs.remove(abs.get(i));
        }

        //abs.add(5);
        abs.removeIf(a -> a.equals(4));
        System.out.println(abs);

    }

    @Test
    public void test34() {
        String key = "whitelist:import:progress:{appId}:{whiteListSno}";
        key = key.replaceAll("\\{[appId}]*\\}", "001");
        key = key.replaceAll("\\{[whiteListSno}]*\\}", "001");

        System.out.println(key);
    }

    @Test
    public void test35() {
        int count = 10000;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(4);
        numberFormat.setMinimumIntegerDigits(4);
        String newWhiteListSno = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + numberFormat.format(count);

        System.out.println(newWhiteListSno);
    }

    @Test
    public void fileTest() {
        System.out.println(FilenameUtils.getExtension("D:\\idea_workspace\\devloper-mine\\aopi.sql"));
    }

    @Test
    public void fileTest2() {
        File file = new File("D:\\data\\kol\\test");
        if (!file.exists()) {
            file.mkdirs();
        }

        try (OutputStream outputStream = new FileOutputStream("D:\\data\\kol\\test\\test.xls")) {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dateTest() {
        LocalDate localDate1 = LocalDate.of(2020, 1, 1);
        LocalDate localDate2 = LocalDate.of(2020, 7, 1);

        Date dt = TimeUtils.parseDate(localDate1);
        Date dp = TimeUtils.parseDate(localDate2);

        long days = localDate1.until(localDate2, ChronoUnit.DAYS);
        System.out.println(days);

    }

    @Test
    public void validateTest() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("123456");
        loginDto.setPassword("12345678");

        JSRValidatorUtil.validate(loginDto);
    }

    @Test
    public void cott() {
        Integer a = -128;
        Integer b = -128;

        System.out.println(a == b);
    }

    @Test
    public void yyyy() {
        String spl = "a,b,c,,,,,h,i";
        for (String v : spl.split(",")) {
            System.out.println(v);
        }

        String spl2 = "a,b,c,,,,,";
        for (String v : spl2.split(",")) {
            System.out.println(v);
        }
    }

    @Test
    public void nioTest() {

    }

    @Test
    public void unicode() {
        String sub = "\u0001|\u0001";
        String unicode = "213213\u0001|\u0001321313";

        String[] arr = unicode.split(sub.replace("|", "[|]"));
        System.out.println(arr);

        System.out.println("|||||".replace("|", "[|]"));
    }

    @Test
    public void testss() {
        String xx = "2021-09-09 16:59:55:00";
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void testyu() {
        System.out.println("123".substring(4, 6));
    }


    /**
     * 期望值：1 2
     * 实际值：1 1
     **/
    @Test
    public void testThreadLocal() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

        Semaphore semaphore = new Semaphore(0);
        Runnable runnable = () -> {
            String v = threadLocal.get();
            System.out.println(v);
            semaphore.release();
        };

        threadLocal.set("1");
        executorService.execute(runnable);
        semaphore.acquire();
        threadLocal.remove();

        threadLocal.set("2");
        executorService.execute(runnable);
        semaphore.acquire();
        threadLocal.remove();

    }

    @Test
    public void testTTL() throws InterruptedException {
        int cap = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(cap);
        TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

        Semaphore lock = new Semaphore(0);
        Runnable runnable = () -> {
            String v = threadLocal.get();
            System.out.println("当前线程ID：" + Thread.currentThread().getId() + "，cache v = " + v);
            lock.release();
        };
        TtlRunnable ttlRunnable;

        threadLocal.set("1");
        ttlRunnable = TtlRunnable.get(runnable);
        executorService.execute(Objects.requireNonNull(ttlRunnable));
        lock.acquire();

        threadLocal.set("2");
        ttlRunnable = TtlRunnable.get(runnable);
        executorService.execute(Objects.requireNonNull(ttlRunnable));
        lock.acquire();

//        for (int i = 1; i <= cap; i++) {
//            threadLocal.set(String.valueOf(i));
//            // 即使是同一个Runnable任务多次提交到线程池时，每次提交时都需要通过修饰操作（即TtlRunnable.get(task)）以抓取这次提交时的TransmittableThreadLocal上下文的值。
//            // https://github.com/alibaba/transmittable-thread-local/tree/v2.12.2
//            ttlRunnable = TtlRunnable.get(runnable);
//            for (int j = 0; j < 3; j++) {
//                executorService.execute(Objects.requireNonNull(ttlRunnable));
//                lock.acquire();
//            }
//            System.out.println("下一批次");
//        }
    }

    @Test
    public void xk() throws ExecutionException, InterruptedException {
        int a = 200, b = 6;
        // 或运算
        System.out.println(100 | 6);
        // 与运算
        System.out.println(100 & 6);
        // 异或运算
        System.out.println(100 ^ 6);

        System.out.println(100 / 6);

        CompletableFuture<?> future = CompletableFuture.runAsync(() -> {
            throw new RuntimeException();
        });

        future.get();
    }

    @Test
    public void getChar() {
        String dst = "hello ";
        String src = "world";
        char[] value = Arrays.copyOf(dst.toCharArray(), 11);
        System.out.println(value);
        src.getChars(0, src.length(), value, dst.length());
        System.out.println(value);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put("1".getBytes());
        buffer.get();
        buffer.put("2".getBytes());
    }

    @Test
    public void ioRead() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("src/test/java/com/loper/mine/SQLParserTest.java"));
            byte[] receive = new byte[8];
            // IO 流读文件的时候不会管 byte 中的数据是否已被处理过，下一次读取直接覆盖
            while (fileInputStream.read(receive) > 0) {
                System.out.println(new String(receive));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void bufferRead() {
        int capacity = 8;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(new File("src/test/java/com/loper/mine/SQLParserTest.java"));
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader, capacity);

            CharBuffer receive = CharBuffer.allocate(capacity);
            char[] data = new char[capacity];
            // buffer reader 在读取数据的时候会判断buffer 中的数据是否已被清理
            while (bufferedReader.read(receive) > 0) {
                receive.flip();
                receive.get(data);
                receive.flip();
                System.out.println(new String(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void mapFile() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            File file = new File("src/test/java/com/loper/mine/SQLParserTest.java");
            inputStream = new FileInputStream(file);

            inChannel = inputStream.getChannel();

            // map 不会进行数据拷贝，会在物理内存开辟一块文件映射区域，只占用物理内存。
            //MappedByteBuffer buffer = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            //ByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

            ByteBuffer buffer = ByteBuffer.allocate(8);
            // read 会进行数据拷贝，会占用用户内存空间。
            inChannel.read(buffer);
            buffer.flip();
            System.out.println(buffer.mark());

            File outFile = new File("src/test/java/com/loper/mine/1.txt");
            outputStream = new FileOutputStream(outFile);
            outChannel = outputStream.getChannel();
            outChannel.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void gatherWrite() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            File file = new File("src/test/java/com/loper/mine/SQLParserTest.java");
            inputStream = new FileInputStream(file);
            inChannel = inputStream.getChannel();

            ByteBuffer buffer1 = ByteBuffer.allocate(8);
            ByteBuffer buffer2 = ByteBuffer.allocate(15);
            ByteBuffer[] buffers = new ByteBuffer[]{buffer1, buffer2};

            // 分散读取
            inChannel.read(buffers);
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
                System.out.println(buffer.mark());
            }

            File outFile = new File("src/test/java/com/loper/mine/1.txt");
            outputStream = new FileOutputStream(outFile);
            outChannel = outputStream.getChannel();
            // 聚集写入
            outChannel.write(buffers);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void send() {
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            // 设置为非阻塞
            channel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String nextLine = scanner.nextLine();
                buffer.put(nextLine.getBytes());
                buffer.flip();
                channel.send(buffer, new InetSocketAddress("127.0.0.1", 8056));
                buffer.clear();
                if ("over".equals(nextLine))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void receive() {
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            // 设置为非阻塞
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(8056));

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            while (true) {
                int select = selector.select();
                boolean exit = false;

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    if (selectionKey.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.receive(buffer);
                        buffer.flip();
                        byte[] data = new byte[buffer.limit()];
                        buffer.get(data);
                        String str = new String(data);
                        System.out.println("收到：" + str);
                        if ("over".equals(str))
                            exit = true;
                    }
                    iterator.remove();
                }
                if (exit)
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void mark() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        Bingo bingo = new Bingo();
        bingo.setP(1);
        bingo.setB(false);
        bingo.setBingo(new Bingo());
        // 查看对象内部结构
        System.out.println(ClassLayout.parseInstance(bingo).toPrintable());
        System.out.println("\n++++++++++++++++++++++++++\n");
        synchronized (bingo) {
            // 查看对象内部结构
            System.out.println(ClassLayout.parseInstance(bingo).toPrintable());
        }
        // 查看对象内部结构
        //System.out.println(ClassLayout.parseInstance(bingo).toPrintable());
        // 查看对象外部信息
        //System.out.println(GraphLayout.parseInstance(bingo).toPrintable());
        // 查看对象总大小
        //System.out.println(GraphLayout.parseInstance(bingo).totalSize());
    }

    @Test
    public void mark2() {
        Bingo[] bingos = new Bingo[1];
        Bingo bingo = new Bingo();
        bingo.setP(1);
        bingo.setB(false);
        bingos[0] = bingo;
        // 查看对象内部结构
        System.out.println(ClassLayout.parseInstance(bingos).toPrintable());
        // 查看对象外部信息
        System.out.println(GraphLayout.parseInstance(bingos).toPrintable());
        // 查看对象总大小
        System.out.println(GraphLayout.parseInstance(bingos).totalSize());
    }


    private static class Bingo {
        @Setter
        private int p;
        @Setter
        private boolean b;
        @Setter
        private Bingo bingo;
    }

    @Test
    public void or() {
        HashMap<String, Integer> map = new HashMap<>(300);
        for (int i = 0; i < 300; i++) {
            map.put("" + i, i);
        }

        map.put("234", 23);

        System.out.println(map);
    }

    @Test
    public void ssssa() {
        int i = 10;
        System.out.println(i >>> 1);
        /*
         * 10       =    1010
         * 10 >>> 1 =     101
         *               1111
         */
    }

    @Test
    public void sort() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("1", Collections.singletonList(0));
        map.put("2", Arrays.asList(1, 2, 3));
        map.put("3", Arrays.asList(1, 2, 3, 4, 5, 6));
        map.put("4", Collections.singletonList(9));

        List<List<Integer>> sortedList = map.values().stream().sorted(Comparator.comparing(List::size, Comparator.reverseOrder())).collect(Collectors.toList());

//        List<List<Integer>> sortedList = map.values().stream().sorted(((o1, o2) -> {
//            if (o1.size() == o2.size())
//                return 0;
//            return o1.size() > o2.size() ? -1 : 1;
//        })).collect(Collectors.toList());

        System.out.println(sortedList);
    }

    @Test
    public void selector() throws IOException {
        Selector selector = Selector.open();
        for (int i = 0; i < 3; i++) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress("127.0.0.1", 8360 + i));
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }
        System.out.println("over");
    }

    @Test
    public void channel() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("127.0.0.1", 8366));
        for (int i = 0; i < 3; i++) {
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }
        System.out.println("over");
    }

    @Data
    @AllArgsConstructor
    private class FooBean {
        private int id;
        private int weight;
        private int parentID;
        private int type;

    }

    @Data
    private class GroupBean {
        private int type;
        private int weight;
        private int parentWeight;
    }

    @Test
    public void computeWeight() {
        int cap = 10000;
        List<FooBean> fooBeanList = new ArrayList<>(cap);
//        for (int i = 0; i < cap; i++) {
//            fooBeanList.add(new FooBean(i, i, RandomUtils.nextInt(0, cap), RandomUtils.nextInt(0, 4)));
//        }
        fooBeanList.add(new FooBean(0, 1, 1, 1));
        fooBeanList.add(new FooBean(1, 2, 0, 2));
        fooBeanList.add(new FooBean(2, 3, 3, 1));
        fooBeanList.add(new FooBean(3, 2, 1, 1));
        fooBeanList.add(new FooBean(4, 3, 3, 2));
        fooBeanList.add(new FooBean(5, 2, 4, 3));
        System.out.println(fooBeanList);
        System.out.println("=====================================");

        // fooBean id : fooBean weight
        final Map<Integer, Integer> parentMap = fooBeanList.stream().collect(Collectors.toMap(FooBean::getId, FooBean::getWeight));

        final List<GroupBean> groupBeanList = fooBeanList.stream().collect(Collectors.groupingBy(FooBean::getType)).entrySet().stream().map(entry -> {
            GroupBean groupBean = new GroupBean();
            groupBean.setType(entry.getKey());
            System.out.println("type =" + entry.getKey() + "的foo ID");
            // 这里可以拿到当前type 所有的 foo
            groupBean.setWeight(entry.getValue().stream().peek(foo -> System.out.println(foo.getId())).mapToInt(FooBean::getWeight).sum());
            System.out.println("type =" + entry.getKey() + "的foo parentID");
            groupBean.setParentWeight(entry.getValue().stream().map(FooBean::getParentID).distinct().peek(System.out::println).mapToInt(parentMap::get).sum());
            return groupBean;
        }).collect(Collectors.toList());

        System.out.println(groupBeanList);
    }

    @Test
    public void queue() throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                //TimeUnit.SECONDS.sleep(10);
                synchronousQueue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(synchronousQueue.take());
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        latch.await();
    }

    @Test
    public void priority() throws InterruptedException {
        PriorityQueue<Integer> priorityQueue =
                new PriorityQueue<>(Comparator.comparingInt(Integer::intValue));
        priorityQueue.add(2);
        priorityQueue.add(1);
        priorityQueue.add(3);
        priorityQueue.add(7);
        priorityQueue.add(5);
        priorityQueue.add(10);
        priorityQueue.add(6);

        int size = priorityQueue.size();
        for (int i = 0; i < size; i++) {
            System.out.println(priorityQueue.poll());
        }
    }

}