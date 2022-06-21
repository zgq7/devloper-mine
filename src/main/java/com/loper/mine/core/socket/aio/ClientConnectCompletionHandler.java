package com.loper.mine.core.socket.aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2022/6/21 14:41
 * @description
 **/
public class ClientConnectCompletionHandler implements CompletionHandler<Void, Void> {
    private final static Logger logger = LoggerFactory.getLogger(ClientConnectCompletionHandler.class);

    private final static ByteBuffer HELLO = ByteBuffer.wrap("nice to meet you, aio!".getBytes());
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private AsynchronousSocketChannel asc;
    private final CountDownLatch latch;
    private final InetSocketAddress serverAddress;
    private boolean hello_done = false;

    public ClientConnectCompletionHandler(AsynchronousSocketChannel asc, CountDownLatch latch, InetSocketAddress serverAddress) {
        this.asc = asc;
        this.latch = latch;
        this.serverAddress = serverAddress;
    }

    @Override
    public void completed(Void result, Void attachment) {
        logger.info("客户端与服务端连接成功");

        /*
         * read 事件应该先就绪
         */
        int capacity = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        asc.read(buffer, null, new ReadCompletionHandler(asc, buffer, "服务端"));
        logger.info("客户端读事件准备就绪");

        if (!hello_done) {
            // 发消息给服务端
            asc.write(HELLO, null, new WriteCompletionHandler());
            hello_done = true;
        }

        EXECUTOR_SERVICE.execute(() -> {
            final Scanner scanner = new Scanner(System.in);
            try {
                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    asc.write(ByteBuffer.wrap(nextLine.getBytes()), null, new WriteCompletionHandler());
                }
                scanner.close();
                logger.info("控制台线程结束");
            } catch (Exception e) {
                logger.error("服务端输入将不可用，服务端输入异常：", e);
            } finally {
                this.latch.countDown();
            }
        });
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        logger.error("客户端连接服务端异常：", exc);
        try {
            TimeUnit.SECONDS.sleep(1);
            logger.info("客户端准备重连服务端");
            asc = AsynchronousSocketChannel.open();
        } catch (Exception ignore) {

        }
        asc.connect(this.serverAddress, null, this);
    }
}
