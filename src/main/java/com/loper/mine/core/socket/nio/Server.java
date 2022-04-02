package com.loper.mine.core.socket.nio;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class Server extends Base {

    private final static Logger logger = LoggerFactory.getLogger(Server.class);

    private ServerSocketChannel server;
    private final ExecutorService executorService = Executors.newFixedThreadPool(500);
    private final Scanner scanner = new Scanner(System.in);
    /**
     * 如果要形成 用户ID：Client 这样的对应关系，还是继承SocketChannel 加属性、封装下比较好
     **/
    private final Map<Long, SocketChannel> clientMap = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

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
        // 监听控制台写入
        executorService.execute(() -> {
            try {
                while (scanner.hasNextLine()) {
                    String[] nextLine = scanner.nextLine().split(",");
                    if (nextLine.length != 2)
                        continue;
                    String clientSeq = nextLine[0];
                    if (!StringUtils.isNumeric(clientSeq))
                        continue;
                    SocketChannel client = clientMap.get(Long.valueOf(clientSeq));
                    if (client != null) {
                        client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(nextLine[1].getBytes()));
                        selector.wakeup();
                    } else {
                        logger.info("客户端：{}不存在", clientSeq);
                    }
                }
            } catch (Exception e) {
                logger.error("服务端输入异常：", e);
                logger.error("服务端输入将不可用");
            }
        });
    }

    @Override
    public void listener() {
        while (true) {
            try {
                int select = selector.select();
                logger.info("----------------------------------");
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    // 表面重复轮询
                    keyIterator.remove();

                    // 连接请求（因为一开始就注册的 ACCEPT 事件）
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        if (client.isConnected()) {
                            // 设置为非阻塞
                            client.configureBlocking(false);
                            long seqNo = seq.incrementAndGet();
                            clientMap.put(seqNo, client);

                            String reply = "连接成功，您的编号是：" + seqNo;
                            client.register(selector, SelectionKey.OP_READ);
                            client.write(ByteBuffer.wrap(reply.getBytes()));
                            InetSocketAddress isa = (InetSocketAddress) client.getRemoteAddress();
                            logger.info("接入的客户端IP：{}，port：{}，seq：{}", isa.getAddress(), isa.getPort(), seqNo);
                        }
                    }

                    // 可写
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnected()) {
                            client.configureBlocking(false);
                            client.write((ByteBuffer) key.attachment());
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    }

                    // 可读
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnected()) {
                            try {
                                client.configureBlocking(false);
                                int capacity = 8 * 1024;
                                ByteBuffer buffer = ByteBuffer.allocate(capacity);
                                byte[] receive = new byte[capacity];
                                int len;
                                while ((len = client.read(buffer)) > 0) {
                                    buffer.flip();
                                    buffer.get(receive, 0, len);
                                    logger.info("接收到客户端消息：{}", new String(receive, 0, len));
                                    buffer.clear();
                                }
                            } catch (IOException e) {
                                logger.error("接收客户端消息异常：", e);
                                key.cancel();
                                client.close();
                                removeClient(client);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("服务端异常，即将关闭：", e);
                break;
            }
        }

        // 退出步骤
        try {
            server.close();
            scanner.close();
            executorService.shutdown();
            logger.info("服务端结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeClient(SocketChannel client) {
        Long seqNo = null;
        for (Map.Entry<Long, SocketChannel> entry : clientMap.entrySet()) {
            if (entry.getValue() == client) {
                seqNo = entry.getKey();
                break;
            }
        }
        if (seqNo != null)
            clientMap.remove(seqNo);
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.bindAndRegister("127.0.0.1", 8366);
        server.listener();
    }

}
