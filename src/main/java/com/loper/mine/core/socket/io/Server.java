package com.loper.mine.core.socket.io;

import com.alibaba.fastjson.JSON;
import com.loper.mine.core.socket.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2019-08-14 13:52.
 *
 * @author zgq7
 */
public class Server extends Base {


    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * socket 服务器占用的端口
     **/
    private static final int SERVER_SOCKET_PORT = 8366;
    /**
     * socket 服务器所能接收的最大连接数
     **/
    private static final int SERVER_CONNECT_MAX_NUM = 50;

    private final Map<String, Socket> socketMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(500);

    private ServerSocket server;

    @Override
    public void init() {
        try {
            server = new ServerSocket(SERVER_SOCKET_PORT, SERVER_CONNECT_MAX_NUM);
            logger.info("服务器已成功启动，服务器ip地址：{}", server.getInetAddress());
        } catch (IOException e) {
            logger.error("服务端初始化异常：", e);
        }
    }

    /**
     * 监听服务端控制台的输入
     **/
    @Override
    public void writeListening() {
        executorService.execute(() -> {
            logger.info("控制台写入监听中...");
            while (true) {
                try {
                    String nextLine = new Scanner(System.in).nextLine();
                    if (isMsgJson(nextLine)) {
                        MessageEntity messageEntity = JSON.parseObject(nextLine, MessageEntity.class);
                        Socket socket = socketMap.get(messageEntity.getIp());
                        sendToClient(socket, messageEntity.getMessage());
                    }
                } catch (Exception e) {
                    logger.error("写字板退出");
                    break;
                }
            }
        });
    }

    /**
     * 监听外部客户端连接
     **/
    public void acceptListening() {
        for (; ; ) {
            Socket socket;
            try {
                socket = this.server.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (socket.isConnected())
                logger.info("新的客户端已连接，客户端ip地址为：{}", socket.getInetAddress());

            this.socketMap.putIfAbsent(getSocketKey(socket), socket);

            executorService.execute(() -> readListening(socket));
        }
    }

    /**
     * 监听外部客户端的消息
     **/
    @Override
    public void readListening(Socket socket) {
        for (; ; ) {
            try {
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String msg = dataInputStream.readUTF();
                logger.info("来源IP：{}，新消息：{}", socket.getInetAddress(), msg);
                if (isMsgJson(msg)) {
                    MessageEntity messageEntity = JSON.parseObject(msg, MessageEntity.class);
                    Socket toSocket = socketMap.get(messageEntity.getIp());
                    sendToClient(toSocket, messageEntity.getMessage());
                }
            } catch (Exception e) {
                logger.error("监听客户端消息异常：", e);
                logger.warn("即将关闭客户端：{} 的连接", getSocketKey(socket));
                socketMap.remove(getSocketKey(socket));
                break;
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.writeListening();
        server.acceptListening();
    }


}
