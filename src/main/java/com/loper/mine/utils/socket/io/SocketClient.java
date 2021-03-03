package com.loper.mine.utils.socket.io;

import com.loper.mine.config.LocalThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created on 2019-08-14 14:16.
 *
 * @author zgq7
 */
public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    /**
     * 服务器IP地址
     **/
    private static final String SOCKET_SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        if (isPassIP(SOCKET_SERVER_IP)) {
            try {
                Socket socket = new Socket(SOCKET_SERVER_IP, 8366, Inet4Address.getByName("127.0.0.8"), 4366);
                logger.info("socket 客户端已成功启动，服务器ip地址：{},本机ip地址：{}", socket.getInetAddress(), socket.getLocalAddress());

                LocalThreadPool.getInstance().execute(() -> {
                    try {
                        MessageUtils.sendTextMsg(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                LocalThreadPool.getInstance().execute(() -> {
                    try {
                        MessageUtils.recieveTextMsg(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                logger.info("收发线程启动完毕");

            } catch (ConnectException e) {
                logger.error("服务器连接失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试ip是否可ping通
     **/
    private static boolean isPassIP(String ip) {
        try {
            return InetAddress.getByName(ip).isReachable(3000);
        } catch (IOException e) {
            logger.error("ip：{} 不可用", ip);
            return false;
        }
    }
}
