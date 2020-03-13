package com.dev.utils.socket.io;

import com.dev.config.LocalThreadPool;
import com.dev.utils.socket.MsgModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 2019-08-14 13:52.
 *
 * @author zgq7
 */
public class IOSocketServer {

    public final static Map<String, Socket> socketMap = Collections.synchronizedMap(new TreeMap<>());

    private static final Logger logger = LoggerFactory.getLogger(IOSocketServer.class);

    /**
     * socket 服务器占用的端口
     **/
    private static final int SERVER_SCOKET_PORT = 8366;
    /**
     * scoket 服务器所能接收的最大连接数
     **/
    private static final int SERVER_CONNET_MAX_NUM = 50;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_SCOKET_PORT, SERVER_CONNET_MAX_NUM);
            logger.info("socket 服务器已成功启动，服务器ip地址：{}", serverSocket.getInetAddress());
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket.isConnected())
                    logger.info("新的客户端已连接，客户端ip地址为：{}", socket.getInetAddress());
                IOSocketServer.socketMap.put(String.valueOf(socket.getInetAddress()), socket);

                LocalThreadPool.getInstance().execute(() -> {
                    try {
                        MessageUtils.recieveSendTextMsg(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("服务器关闭失败");
            }
        }
    }

    public static void openConnection(MsgModel msgModel) {
        Socket socket = IOSocketServer.socketMap.get(msgModel.getIp());
        if (socket != null) {
            LocalThreadPool.getInstance().execute(() -> {
                try {
                    MessageUtils.recieveTextMsg(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            LocalThreadPool.getInstance().execute(() -> {
                try {
                    MessageUtils.sendTextMsg(socket, msgModel.getMsg());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            logger.info("收发线程启动完毕");
        }
    }

}
