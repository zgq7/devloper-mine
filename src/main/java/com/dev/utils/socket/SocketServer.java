package com.dev.utils.socket;

import com.dev.config.LocalThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created on 2019-08-14 13:52.
 *
 * @author zgq7
 */
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    /**
     * socket 服务器占用的端口
     **/
    private static final int SERVER_SCOKET_PORT = 8366;
    /**
     * scoket 服务器所能接收的最大连接数
     **/
    private static final int SERVER_CONNET_MAX_NUM = 50;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_SCOKET_PORT, SERVER_CONNET_MAX_NUM);
            logger.info("socket 服务器已成功启动，服务器ip地址：{}", serverSocket.getInetAddress());
            Socket socket = serverSocket.accept();
            if (socket.isConnected())
                logger.info("新的客户端已连接，客户端ip地址为：{}", socket.getInetAddress());

            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            while (true) {
                String msg = dis.readUTF();
                System.out.println("收到客户端信息"+msg);
                Scanner sc = new Scanner(System.in);
                String str = sc.next();
                dos.writeUTF(str);
            }

/*            LocalThreadPool.getInstance().execute(()->{
                try {
                    MessageUtils.recieveTextMsg(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            LocalThreadPool.getInstance().execute(()->{
                try {
                    MessageUtils.sendTextMsg(socket,msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });*/

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

}
