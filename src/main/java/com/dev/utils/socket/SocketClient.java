package com.dev.utils.socket;

import com.dev.config.LocalThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
                Socket socket = new Socket(SOCKET_SERVER_IP, 8366);
                logger.info("socket 客户端已成功启动，服务器ip地址：{}", socket.getInetAddress());

                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                while(true){
                    Scanner sc = new Scanner(System.in);
                    String str = sc.next();
                    dos.writeUTF(str);
                    String msg = dis.readUTF();
                    System.out.println("收到服务端信息"+msg);
                }

/*                LocalThreadPool.getInstance().execute(() -> {
                    try {
                        MessageUtils.sendTextMsg(socket, msg);
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
                });*/

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
