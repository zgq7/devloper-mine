package com.loper.mine.core.socket.aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author liaonanzhou
 * @date 2022/6/21 14:51
 * @description
 **/
public class ReadCompletionHandler implements CompletionHandler<Integer, String> {
    private final static Logger logger = LoggerFactory.getLogger(ReadCompletionHandler.class);

    private final AsynchronousSocketChannel asc;
    private final ByteBuffer readBuffer;
    private final String kol;
    private final StringBuilder msgSB = new StringBuilder();

    public ReadCompletionHandler(AsynchronousSocketChannel asc, ByteBuffer readBuffer, String kol) {
        this.asc = asc;
        this.readBuffer = readBuffer;
        this.kol = kol;
    }

    @Override
    public void completed(Integer result, String attachment) {
        int capacity = readBuffer.capacity();
        byte[] receive = new byte[capacity];
        readBuffer.flip();
        readBuffer.get(receive, 0, result);
        String msg = new String(receive, 0, result);
        this.msgSB.append(msg);

        if (msg.length() < capacity) {
            try {
                InetSocketAddress address = (InetSocketAddress) asc.getRemoteAddress();
                logger.info("接收到{}ip={}的消息：{}", kol, address.getHostName(), msgSB.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // 清空缓存区
            this.msgSB.setLength(0);
        }
        readBuffer.clear();
        // 继续接受后续的消息
        asc.read(readBuffer, attachment, this);
    }

    @Override
    public void failed(Throwable exc, String attachment) {
        logger.error("读取{}消息异常：", kol, exc);
    }
}
