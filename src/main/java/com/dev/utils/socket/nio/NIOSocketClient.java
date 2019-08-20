package com.dev.utils.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created on 2019-08-19 15:58.
 *
 * @author zgq7
 */
public class NIOSocketClient implements NIOBaseSocket {

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
    /**
     * 客户端的client key
     **/
    private SelectionKey key = null;

    /**
     * @param ip   远程服务器地址
     * @param port 远程服务器端口
     **/
    @Override
    public void init(String ip, int port) throws IOException {
        SocketChannel client = SocketChannel.open();

        client.configureBlocking(false);

        this.selector = Selector.open();

        client.connect(new InetSocketAddress(ip, port));

        key = client.register(selector, SelectionKey.OP_CONNECT);

        System.out.println("client 启动成功 ");

    }

    @Override
    public void getNewMsg() throws IOException {
        if (key != null)
            while (true) {
                try {
                    //key可读时
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
            }

    }

    @Override
    public void registry(SelectionKey key) throws IOException {
    }

    @Override
    public void readMsg(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        this.readBuffer.clear();
        client.read(this.readBuffer);
        byte[] bytes = new byte[this.readBuffer.position()];
        this.readBuffer.get(bytes);
        String msg = new String(bytes).trim();

        //flip方法可使buffer中的ops归0
        this.readBuffer.flip();
        client.register(this.selector, SelectionKey.OP_WRITE);
        System.out.println("server msg：" + msg);
    }

    @Override
    public void writeMsg(SelectionKey key) throws IOException {
        this.writeBuffer.clear();
        SocketChannel Client = (SocketChannel) key.channel();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();

        this.writeBuffer = ByteBuffer.wrap(msg.getBytes());
        Client.write(this.writeBuffer);
        Client.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        NIOSocketClient client = new NIOSocketClient();
        client.init("127.0.0.1", 8366);
        client.getNewMsg();
    }

}
