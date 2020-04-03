package com.dev;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dev.config.LocalThreadPool;
import com.dev.model.Aopi;
import com.dev.utils.dxc.Dxc1;
import com.dev.utils.dxc.Dxc2;
import com.dev.utils.sjms.GlassCarrier;
import com.dev.utils.sjms.GonPen;
import com.dev.utils.sjms.PaperCarrier;
import com.dev.utils.sjms.PenFactory;
import com.dev.utils.time.TimeUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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

	@Test
	public void test04() {
		GonPen gonPen = PenFactory.newGonPen();

		System.out.println(gonPen.canBeClean() + "   " + gonPen.canBeWrite() + "   " + gonPen.getWritableLength());

		gonPen.writeOn(new GlassCarrier());
		System.out.println(gonPen.canBeClean() + "   " + gonPen.canBeWrite() + "   " + gonPen.getWritableLength());

		gonPen.writeOn(new PaperCarrier());
		System.out.println(gonPen.canBeClean() + "   " + gonPen.canBeWrite() + "   " + gonPen.getWritableLength());
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
	public void test061() {
		int[] test = new int[]{5, 8, 4, 1, 6, 3, 7, 9, 10, 11};
		test06(test, 0, 8);
		for (int p = 0; p < test.length; p++) {
			System.out.print(test[p] + ",");
		}
	}

	private void test06(int[] test, int low, int high) {
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
		test06(test, low, j - 1);
		test06(test, j + 1, high);
	}

	/**
	 * localthread
	 **/
	@Test
	public void test07() throws InterruptedException {
		ThreadLocal<Integer> threadLocal = new ThreadLocal();
		ThreadLocal<String> threadLocal1 = new ThreadLocal();

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
	 * Callable测试
	 **/
	@Test
	public void test08() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future future1 = executorService.submit(new Dxc1());
		Future future2 = executorService.submit(new Dxc2());

		Thread.sleep(10000);
		StringBuffer s = new StringBuffer("123");
		s.append("4");
		System.out.println(s);
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
	}

	/**
	 * Semaphore
	 **/
	@Test
	public void test10() {
		Thread main = Thread.currentThread();
		Semaphore semaphore = new Semaphore(3);//三个资源，公平锁

		//6线程抢占三个资源
		int p = 0;
		for (int i = 1; i < 7; i++) {
			new Thread(() -> {
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

		while (p != 6) {

		}
	}

	/**
	 * 闭锁
	 **/
	@Test
	public void test12() {
		CountDownLatch latch = new CountDownLatch(2);
		new Thread(() -> {
			System.out.println(Thread.currentThread().getId());
			latch.countDown();
		}).start();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName());
			latch.countDown();
		}).start();

		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("success");
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
	public void test20(){
		List<String> s = new ArrayList<>();
		s.add("1");
		s.add("1");
		s.add("1");
		System.out.println(Arrays.toString(s.toArray()));
		System.out.println(String.join(",",s));
	}


}
