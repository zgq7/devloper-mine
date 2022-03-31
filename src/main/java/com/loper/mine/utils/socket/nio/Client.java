package com.loper.mine.utils.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
        executorService.execute(() -> {
            try {
                // 客户端连接成功后才进行控制台写入
                while (!client.isConnected()) {
                    TimeUnit.SECONDS.sleep(1);
                }
                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    if (client.isOpen()) {
                        //用户已输入，注册写事件，将输入的消息发送给客户端
                        client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(nextLine.getBytes()));
                        //唤醒之前因为监听OP_READ而阻塞的select()
                        selector.wakeup();
                    }
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
                int select = selector.select();
                logger.info("----------------------------------");
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
                break;
            }
        }

        // 退出步骤
        try {
            client.close();
            scanner.close();
            executorService.shutdown();
            logger.info("客户端退出连接");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.bindAndRegister("127.0.0.1", 8366);
        client.listener();
    }

}
