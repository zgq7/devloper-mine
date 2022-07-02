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


    public static void main(String[] args) throws IOException {
        Server server = new Server("127.0.0.1", 8366);
        server.bindAndRegister();
        server.listener();
    }

    public Server(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void bindAndRegister() throws IOException {
        selector = Selector.open();

        this.server = ServerSocketChannel.open();
        // 设置为非阻塞
        server.configureBlocking(false);
        // 绑定端口
        server.socket().bind(new InetSocketAddress(this.ip, this.port));
        // 注册到selector上
        server.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("服务端启动成功,ip:{},port:{}", this.ip, this.port);
        // 监听控制台写入
        executorService.execute(() -> {
            try {
                while (scanner.hasNextLine()) {
                    if (!this.server.isOpen())
                        break;
                    String nextLine = scanner.nextLine();
                    if (OVER.equals(nextLine)) {
                        this.terminal();
                        break;
                    }
                    String[] nextLineArr = scanner.nextLine().split(",");
                    if (nextLineArr.length != 2)
                        continue;
                    String clientSeq = nextLineArr[0];
                    if (!StringUtils.isNumeric(clientSeq))
                        continue;
                    SocketChannel client = clientMap.get(Long.valueOf(clientSeq));
                    if (client != null) {
                        client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(nextLineArr[1].getBytes()));
                        /*
                         * 若 selector 处于 select 阻塞中，
                         * 此时新 register 一个事件是无法扫描到的，需要 wakeUp 一下阻塞线程，或重新进行 select 操作。
                         */
                        selector.wakeup();
                    } else {
                        logger.info("客户端：{}不存在", clientSeq);
                    }
                }
                this.scanner.close();
                logger.info("控制台线程结束");
            } catch (Exception e) {
                logger.error("服务端输入异常：", e);
                logger.error("服务端输入将不可用");
            }
        });
    }

    @Override
    public void listener() throws IOException {
        while (true) {
            try {
                int select = selector.select();
                if (!selector.isOpen())
                    break;
                logger.info("----------------------------------select={}", select);
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
                                // socket channel连接断开时会触发一个read为-1的事件
                                if (len == -1) {
                                    closeAndRemoveClient(client);
                                }
                            } catch (IOException e) {
                                logger.error("接收客户端消息异常：", e);
                                key.cancel();
                                closeAndRemoveClient(client);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("服务端异常，即将关闭：", e);
                break;
            }
        }

        terminal();
    }

    @Override
    void terminal() throws IOException {
        // 退出步骤
        try {
            if (this.server.isOpen())
                this.server.close();
            if (this.selector.isOpen())
                this.selector.close();

            this.scanner.close();
            if (!this.executorService.isTerminated()) {
                this.executorService.shutdownNow();
                logger.info("服务端结束");
            }
        } catch (IOException e) {
            logger.error("客户端退出异常：", e);
            throw e;
        }
    }

    /**
     * 移除客户端
     **/
    private void closeAndRemoveClient(SocketChannel client) {
        if (client.isOpen()) {
            try {
                client.close();
            } catch (IOException ioException) {
                logger.error("关闭客户端异常：", ioException);
            }
        }
        Long seqNo = null;
        for (Map.Entry<Long, SocketChannel> entry : clientMap.entrySet()) {
            if (entry.getValue() == client) {
                seqNo = entry.getKey();
                break;
            }
        }
        if (seqNo != null)
            clientMap.remove(seqNo);

        logger.info("客户端卸载成功，序号：{}", seqNo);
    }

}
