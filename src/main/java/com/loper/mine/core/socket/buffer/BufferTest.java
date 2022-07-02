package com.loper.mine.core.socket.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2022/3/29 10:43
 * @description
 **/
public class BufferTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        String bufferData = "hello world";
        int capacity = 4;
        // 默认使用分配堆内存分配缓冲区空间（非直接缓冲区）
        //ByteBuffer buffer = ByteBuffer.allocate(capacity);
        // 使用直接内存分配缓冲区空间（直接缓冲区）
        ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);

        Semaphore semaphore1 = new Semaphore(0);
        Semaphore semaphore2 = new Semaphore(0);
        // 写操作
        executor.execute(() -> {
            int index = 0, len = bufferData.length();
            while (index < len) {
                try {
                    System.out.println("put数据开始----------------");
                    print(buffer);
                    int endIndex = index + capacity;
                    if (endIndex > len)
                        endIndex = len;

                    // 存之前先清空buffer
                    buffer.clear();
                    buffer.put(bufferData.substring(index, endIndex).getBytes());

                    System.out.println("put数据结束----------------");
                    print(buffer);
                    System.out.println("\n");
                    // 存完告诉读线程可读区域大小
                    buffer.flip();

                    index += capacity;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                } finally {
                    semaphore2.release();
                    try {
                        semaphore1.tryAcquire(3, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 读操作
        executor.execute(() -> {
            StringBuilder value = new StringBuilder();
            int i = 0;
            while (i < bufferData.length()) {
                try {
                    semaphore2.tryAcquire(3, TimeUnit.SECONDS);
                    System.out.println("get数据开始----------------");
                    print(buffer);

                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    value.append(new String(bytes));

                    System.out.println("get数据结束----------------");
                    print(buffer);
                    System.out.println("\n");

                    i += bytes.length;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                } finally {
                    semaphore1.release();
                }
            }

            // 完整读取到的buffer数据
            System.out.println("完整读取到的buffer数据：" + value.toString());
            buffer.clear();
            print(buffer);
        });

        executor.shutdown();
    }

    private static void print(Buffer buffer) {
        System.out.println("position=" + buffer.position());
        System.out.println("limit   =" + buffer.limit());
        System.out.println("capacity=" + buffer.capacity());
        System.out.println("mark    ：" + buffer.mark());
    }

}
