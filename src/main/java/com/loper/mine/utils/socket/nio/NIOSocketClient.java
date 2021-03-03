package com.loper.mine.utils.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

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
        this.selector = Selector.open();

        client.configureBlocking(false);

        client.connect(new InetSocketAddress(ip, port));

        this.key = client.register(selector, SelectionKey.OP_CONNECT);
        System.out.println(this.selector.hashCode() + " -> client 启动成功 ");
    }

    @Override
    public void getNewMsg() throws IOException {
        int i = 0;
        while (this.selector.select() > 0) {
            Set<SelectionKey> keySet = this.selector.selectedKeys();
            for (SelectionKey key : keySet) {
                if (key.isConnectable()) {
                    System.out.println("reg");
                    registry(key);
                }
                //key可读时
                if (key.isReadable()) {
                    readMsg(key);
                }
                //key可写时
                if (key.isWritable() && key.isValid()) {
                    writeMsg(key);
                }
                keySet.remove(key);
            }
            i++;
        }
    }

    @Override
    public void registry(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        if (client.isConnectionPending()) {
            client.finishConnect();
        }
        client.configureBlocking(false);

        String msg = "hello";
        client.write(ByteBuffer.wrap(msg.getBytes()));
        client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    @Override
    public void readMsg(SelectionKey key) throws IOException {
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

        System.out.println("server msg：" + msg);
        this.readBuffer.clear();
    }

    @Override
    public void writeMsg(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        this.writeBuffer = ByteBuffer.wrap(msg.getBytes());
        client.write(this.writeBuffer);

        this.writeBuffer.clear();
    }

    public static void main(String[] args) throws IOException {
        NIOSocketClient client = new NIOSocketClient();
        client.init("127.0.0.1", 8366);
        client.getNewMsg();
    }

}
