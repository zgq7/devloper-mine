package com.loper.mine.core.socket.aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2022/6/20 17:19
 * @description
 **/
public class Server {
    private final static Logger logger = LoggerFactory.getLogger(Server.class);
    public final static int PORT = 8633;

    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open();

            assc.bind(new InetSocketAddress(PORT));

            assc.accept(null, new ServerAcceptCompletionHandler(assc));

            // 防止用户线程退出
            logger.info("客户端即将阻塞退出");
            while (true) {
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            logger.error("服务端异常：", e);
        }
    }
}
