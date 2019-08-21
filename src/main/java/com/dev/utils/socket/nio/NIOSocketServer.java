package com.dev.utils.socket.nio;

import com.dev.config.LocalThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class NIOSocketServer implements NIOBaseSocket {

    /**
     * 初始化一个select用于轮询监视
     **/
    private Selector selector;
    /**
     * 用于读取数据的buffer
     **/
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    /**
     * 用于传输数据的buffer
     **/
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    @Override
    public void init(String ip, int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        this.selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(ip, port));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println(this.selector.hashCode() + " ->server 启动成功");
    }

    @Override
    public void getNewMsg() throws IOException {
        int i = 0;
        while (this.selector.select() > 0) {
            Set<SelectionKey> selectionKeySet = this.selector.selectedKeys();
            selectionKeySet.forEach(key -> {
                try {
                    //测试该管道是否可以建立一个新的socket连接
                    if (key.isAcceptable()) {
                        System.out.println("reg");
                        registry(key);
                    }
                    //key 可读时
                    if (key.isReadable()) {
                        readMsg(key);
                    }
                    //key可写时
                    if (key.isWritable() && key.isValid()) {
                        LocalThreadPool.getInstance().execute(() -> {
                            try {
                                writeMsg(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectionKeySet.remove(key);
            });

            i++;
        }

    }

    @Override
    public void registry(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        SocketChannel client = server.accept();
        client.configureBlocking(false);

        String msg = "hello";
        client.write(ByteBuffer.wrap(msg.getBytes()));
        client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    @Override
    public void readMsg(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        this.readBuffer.clear();
        try {
            int readsum = client.read(this.readBuffer);
            int position = this.readBuffer.position();
            //flip方法可使buffer中的ops归0
            this.readBuffer.flip();

            byte[] bytes = new byte[position];
            for (int i = 0; i < position; i++) {
                bytes[i] = this.readBuffer.get();
            }
            String msg = new String(bytes).trim();

            System.out.println("client msg：" + msg);
            this.readBuffer.clear();
        } catch (IOException e) {
            this.selector.selectedKeys().remove(key);
            System.out.println("远程主机或已关闭，获取信息失败");
        }
    }

    @Override
    public void writeMsg(SelectionKey key) throws IOException {
        SocketChannel toClient = (SocketChannel) key.channel();

        //String msg = "1";
        String msg = new Scanner(System.in).nextLine();
        this.writeBuffer = ByteBuffer.wrap(msg.getBytes());
        toClient.write(this.writeBuffer);

        this.writeBuffer.clear();
        //toClient.register(this.selector, SelectionKey.OP_READ, this.writeBuffer);
    }

    public static void main(String[] args) throws IOException {
        NIOSocketServer server = new NIOSocketServer();
        server.init("127.0.0.1", 8366);
        server.getNewMsg();
    }


}
