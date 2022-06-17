package com.loper.mine.core.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class Client extends Base {

    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    private SocketChannel client;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Scanner scanner = new Scanner(System.in);
    /**
     * 是否需要断开连接
     **/
    protected volatile boolean disConnect = false;

    public static void main(String[] args) throws IOException {
        Client client = new Client("127.0.0.1", 8366);
        client.bindAndRegister();
        client.listener();
    }

    public Client(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void bindAndRegister() throws IOException {
        this.selector = Selector.open();

        connectAndRegister();

        logger.info("客户端启动,ip:{},port:{}", ip, port);
        // 监听控制台写入
        executorService.execute(() -> {
            Thread.currentThread().setName("console-thread");
            try {
                // 客户端连接成功后才进行控制台写入
                while (!client.isConnected()) {
                    TimeUnit.SECONDS.sleep(1);
                    if (!client.isOpen())
                        break;
                }
                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    if (client.isOpen()) {
                        if (OVER.equals(nextLine)) {
                            this.terminal();
                            break;
                        }
                        //用户已输入，注册写事件，将输入的消息发送给客户端
                        client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(nextLine.getBytes()));
                        //唤醒之前因为监听OP_READ而阻塞的select()
                        selector.wakeup();
                    }
                }
                logger.info("控制台线程结束");
            } catch (Exception e) {
                logger.error("控制台输入异常：", e);
            }
        });
    }

    /**
     * 连接并注册
     **/
    private void connectAndRegister() throws IOException {
        this.client = SocketChannel.open();
        this.client.configureBlocking(false);
        this.client.connect(new InetSocketAddress(ip, port));
        this.client.register(selector, SelectionKey.OP_CONNECT);
    }

    @Override
    public void listener() throws IOException {
        while (true) {
            try {
                int select = selector.select();
                if (!selector.isOpen())
                    break;
                logger.info("----------------------------------select={}", select);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> keyIterable = selectionKeySet.iterator();
                while (keyIterable.hasNext()) {
                    SelectionKey key = keyIterable.next();
                    // 避免重复轮询
                    keyIterable.remove();

                    // 连接中（因为一开始就注册的 CONNECT 事件）
                    if (key.isConnectable()) {
                        while (!client.finishConnect()) {
                            logger.info("连接中");
                            TimeUnit.SECONDS.sleep(1);
                        }
                        logger.info("连接成功");
                        // 监听服务端来的消息
                        client.register(selector, SelectionKey.OP_READ);
                    }

                    // 可写
                    if (key.isWritable()) {
                        client.write((ByteBuffer) key.attachment());
                        client.register(selector, SelectionKey.OP_READ);
                    }

                    // 可读
                    if (key.isReadable()) {
                        try {
                            int capacity = 8 * 1024;
                            ByteBuffer buffer = ByteBuffer.allocate(capacity);
                            byte[] receive = new byte[capacity];
                            int len;
                            while ((len = client.read(buffer)) > 0) {
                                buffer.flip();
                                buffer.get(receive, 0, len);
                                logger.info("接收到服务端消息：{}", new String(receive, 0, len));
                                buffer.clear();
                            }
                        } catch (IOException e) {
                            logger.error("接收服务端消息异常：", e);
                            key.cancel();
                            throw e;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("连接异常：", e);
                if (!this.disConnect && (e instanceof ConnectException)) {
                    logger.info("客户端即将进行重连");
                    connectAndRegister();
                } else {
                    break;
                }
            }
        }

        terminal();
    }

    @Override
    void terminal() throws IOException {
        // 退出步骤
        try {
            if (this.client.isOpen())
                this.client.close();
            if (this.selector.isOpen())
                this.selector.close();

            this.scanner.close();
            if (!executorService.isTerminated()){
                executorService.shutdown();
                logger.info("客户端退出连接");
            }
        } catch (Exception e) {
            logger.error("客户端退出异常：", e);
            throw e;
        }
    }
}
