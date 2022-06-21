package com.loper.mine.core.socket.aio;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author liaonanzhou
 * @date 2022/6/20 18:06
 * @description
 **/
public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", Server.PORT);
        String localIP = "127.0.0." + RandomUtils.nextInt(0, 254);
        int localPort = RandomUtils.nextInt(15600, 15699);
        logger.info("客户端ip:{}，客户端port:{}", localIP, localPort);
        try {
            AsynchronousSocketChannel asc = AsynchronousSocketChannel.open();

            asc.bind(new InetSocketAddress(localIP, localPort));
            asc.connect(serverAddress, null, new ClientConnectCompletionHandler(asc, latch, serverAddress));

            logger.info("客户端即将阻塞退出");
            // 防止用户线程退出
            latch.await();
        } catch (Exception e) {
            logger.error("客户端异常：", e);
        }
    }
}
