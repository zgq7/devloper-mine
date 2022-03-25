package com.loper.mine.utils.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Created on 2019-08-19 16:08.
 *
 * @author zgq7
 * @apiNote selectionKey: 注册到selector 上的 channel
 * https://blog.csdn.net/weixin_42762133/article/details/100040141
 */
public abstract class AbstractSocket {
    private final Logger logger = LoggerFactory.getLogger(AbstractSocket.class);

    /**
     * 初始化一个select用于轮询监视
     **/
    protected Selector selector;
    /**
     * 用于读取数据的buffer
     **/
    protected ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    /**
     * 用于传输数据的buffer
     **/
    protected ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    /**
     * 绑定端口并注册到管道
     *
     * @param ip   客户端/服务端 IP
     * @param port 客户端/服务端 port
     **/
    abstract void bindAndRegister(String ip, int port) throws IOException;

    /**
     * 监听每个key 是否可读、可写 等状态
     **/
    abstract void listener() throws IOException;

    /**
     * 监听消息接收
     **/
    void listenAcceptMsg(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        this.readBuffer.clear();
        client.read(this.readBuffer);
        int position = this.readBuffer.position();
        //flip方法可使buffer中的ops归0
        this.readBuffer.flip();

        byte[] bytes = new byte[position];
        for (int i = 0; i < position; i++) {
            bytes[i] = this.readBuffer.get();
        }
        String msg = new String(bytes).trim();

        logger.info("received msg：{}", msg);
        this.readBuffer.clear();
    }


}
