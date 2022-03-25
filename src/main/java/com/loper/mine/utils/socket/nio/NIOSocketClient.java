package com.loper.mine.utils.socket.nio;

import com.loper.mine.config.LocalThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class NIOSocketClient extends AbstractSocket {

    private final static Logger logger = LoggerFactory.getLogger(NIOSocketServer.class);

    private SocketChannel client;

    @Override
    public void bindAndRegister(String ip, int port) throws IOException {
        this.selector = Selector.open();

        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        client.connect(new InetSocketAddress(ip, port));
        client.register(selector, SelectionKey.OP_CONNECT);
        logger.info("客户端启动成功,ip:{},port:{}", ip, port);
        this.client = client;
        // 监听控制台写入
        LocalThreadPool.getInstance().execute(() -> {
            try {
                // 客户端连接成功后才进行控制台写入
                for (; ; ) {
                    if (client.isConnected())
                        break;
                    else
                        TimeUnit.SECONDS.sleep(1);
                }
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String s = scanner.nextLine();
                    //用户已输入，注册写事件，将输入的消息发送给客户端
                    client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(s.getBytes()));
                    //唤醒之前因为监听OP_READ而阻塞的select()
                    selector.wakeup();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void listener() {
        while (true) {
            try {
                if (selector.select() > 0) {
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    selectionKeySet.forEach(key -> {
                        selectionKeySet.remove(key);

                        // 连接
                        if (key.isConnectable()) {
                            for (; ; ) {
                                try {
                                    if (client.finishConnect()) {
                                        logger.info("连接完成");
                                        break;
                                    } else {
                                        logger.info("连接中");
                                    }
                                } catch (IOException e) {
                                    logger.error("连接异常");
                                    break;
                                }
                            }
                        }

                        if (key.isReadable()){

                        }
                    });
                }
            } catch (Exception e) {
                break;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        NIOSocketClient client = new NIOSocketClient();
        client.bindAndRegister("127.0.0.1", 8366);
        client.listener();
    }

}
