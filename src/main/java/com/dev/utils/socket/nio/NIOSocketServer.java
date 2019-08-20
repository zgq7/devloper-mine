package com.dev.utils.socket.nio;

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

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket().bind(new InetSocketAddress(ip, port));

        this.selector = Selector.open();
        //注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("server 启动成功");
    }

    @Override
    public void getNewMsg() throws IOException {
        while (true) {
            //管道中key的数量
            int keyNumber = selector.select();
            Set<SelectionKey> selectionKeySet = this.selector.selectedKeys();
            selectionKeySet.forEach(key -> {
                try {
                    //测试该管道是否可以建立一个新的socket连接
                    if (key.isAcceptable()) {
                        registry(key);
                    }

                    //key 可读时
                    if (key.isReadable()) {
                        readMsg(key);
                    }

                    //key可写时
                    if (key.isWritable() && key.isValid()) {
                        writeMsg(key);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.selector.selectedKeys().remove(key);
            });
        }

    }

    @Override
    public void registry(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        SocketChannel client = server.accept();
        System.out.println(2);
        client.configureBlocking(false);
        client.register(this.selector, SelectionKey.OP_READ);
    }

    @Override
    public void readMsg(SelectionKey key) throws IOException {
        System.out.println(1);
        SocketChannel client = (SocketChannel) key.channel();
        this.readBuffer.clear();
        client.read(this.readBuffer);
        //flip方法可使buffer中的ops归0
        this.readBuffer.flip();
        byte[] bytes = new byte[this.readBuffer.position()];
        this.readBuffer.get(bytes);
        String msg = new String(bytes).trim();

        System.out.println("client msg：" + msg);
        client.register(this.selector, SelectionKey.OP_WRITE);
    }

    @Override
    public void writeMsg(SelectionKey key) throws IOException {
        System.out.println(3);
        this.writeBuffer.clear();

        SocketChannel toClient = (SocketChannel) key.channel();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();

        this.writeBuffer = ByteBuffer.wrap(msg.getBytes());
        toClient.write(this.writeBuffer);
        toClient.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        NIOSocketServer server = new NIOSocketServer();
        server.init("127.0.0.1", 8366);
        server.getNewMsg();
    }


}
