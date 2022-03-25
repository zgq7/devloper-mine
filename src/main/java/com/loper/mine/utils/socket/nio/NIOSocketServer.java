package com.loper.mine.utils.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class NIOSocketServer extends AbstractSocket {

    private final static Logger logger = LoggerFactory.getLogger(NIOSocketServer.class);

    private ServerSocketChannel server;

    @Override
    public void bindAndRegister(String ip, int port) throws IOException {
        selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();
        // 设置为非阻塞
        server.configureBlocking(false);
        // 绑定端口
        server.socket().bind(new InetSocketAddress(ip, port));
        // 注册到selector上
        server.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("服务端启动成功,ip:{},port:{}", ip, port);
        this.server = server;
    }

    @Override
    public void listener() throws IOException {
        while (true){
            if (server.isOpen()){

            }else
                break;
        }
    }


    public static void main(String[] args) throws IOException {
        NIOSocketServer server = new NIOSocketServer();
        server.bindAndRegister("127.0.0.1", 8366);
        server.listener();
    }

}
