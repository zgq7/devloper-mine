package com.dev.utils.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created on 2019-08-14 16:57.
 *
 * @author zgq7
 */
public class MessageUtils {

    private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    /**
     * 写字板
     **/
    protected static String writeBoard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }


    /**
     * 向客户端接收数字消息
     **/
    protected static void recieveNumberMsg(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        int msg = inputStream.read();
        logger.info("新消息：{}", msg);
        inputStream.close();
    }

    /**
     * 接收客户端字符串消息
     **/
    protected static void recieveTextMsg(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String msg = dataInputStream.readUTF();
        logger.info("新消息：{}", msg);
        inputStream.close();
        //dataInputStream.close();
    }

    /**
     * 向客户端发送数字消息
     **/
    protected static void sendNumberMsg(Socket socket, int msg) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg);
        outputStream.close();
    }

    /**
     * 向客户端发送字符串消息
     **/
    protected static void sendTextMsg(Socket socket, String msg) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(msg);
        outputStream.close();
        //dataOutputStream.close();
    }

}
