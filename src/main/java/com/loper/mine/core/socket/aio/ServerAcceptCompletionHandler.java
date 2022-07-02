package com.loper.mine.core.socket.aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liaonanzhou
 * @date 2022/6/21 14:40
 * @description
 **/
public class ServerAcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, String> {
    private final static Logger logger = LoggerFactory.getLogger(ServerAcceptCompletionHandler.class);

    private final static Map<String, AsynchronousSocketChannel> SOCKET_CHANNEL_MAP = new ConcurrentHashMap<>();
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private final AsynchronousServerSocketChannel assc;
    private boolean hello_done = false;
    private boolean scanner_opened = false;

    public ServerAcceptCompletionHandler(AsynchronousServerSocketChannel assc) {
        this.assc = assc;
    }

    @Override
    public void completed(AsynchronousSocketChannel result, String attachment) {
        try {
            InetSocketAddress address = (InetSocketAddress) result.getRemoteAddress();
            logger.info("客户端ip={}，客户端port={}，附带消息：{}", address.getHostName(), address.getPort(), attachment);
            SOCKET_CHANNEL_MAP.put(address.getHostName(), result);
        } catch (Exception e) {
            logger.error("获取客户端ip、port异常：", e);
            return;
        }

        int capacity = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        // 异步监听客户端消息
        result.read(buffer, attachment, new ReadCompletionHandler(result, buffer, "客户端"));

        if (!hello_done) {
            // 发消息给客户端
            ByteBuffer hello = ByteBuffer.wrap("welcome to aio!".getBytes());
            result.write(hello, null, new WriteCompletionHandler());
            hello_done = true;
        }

        if (!scanner_opened) {
            EXECUTOR_SERVICE.execute(() -> {
                final Scanner scanner = new Scanner(System.in);
                try {
                    while (scanner.hasNextLine()) {
                        String nextLine = scanner.nextLine();
                        String[] arr = nextLine.split(",");
                        AsynchronousSocketChannel asc = SOCKET_CHANNEL_MAP.get(arr[0]);
                        if (asc == null) {
                            continue;
                        }
                        asc.write(ByteBuffer.wrap(arr[1].getBytes()), null, new WriteCompletionHandler());
                    }
                    scanner.close();
                    logger.info("控制台线程结束");
                } catch (Exception e) {
                    logger.error("服务端输入将不可用，服务端输入异常：", e);
                }
            });
            scanner_opened = true;
        }

        // 继续监听后续的连接
        this.assc.accept(attachment, new ServerAcceptCompletionHandler(this.assc));
    }

    @Override
    public void failed(Throwable exc, String attachment) {
        logger.error("服务端接收客户端异常：", exc);
    }
}
