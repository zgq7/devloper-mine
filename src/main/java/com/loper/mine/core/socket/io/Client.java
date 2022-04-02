package com.loper.mine.core.socket.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2019-08-14 14:16.
 *
 * @author zgq7
 */
public class Client extends Base {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    /**
     * 服务器IP地址
     **/
    private final String serverIp = "127.0.0.1";

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private Socket client;

    @Override
    public void init() {
        boolean pingSuccess = false;
        try {
            pingSuccess = InetAddress.getByName(serverIp).isReachable(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pingSuccess) {
            try {
                client = new Socket(serverIp, 8366, Inet4Address.getByName("127.0.0.8"), 4366);
                if (client.isConnected())
                    logger.info("客户端已成功启动，服务器ip地址：{},本机ip地址：{}", client.getInetAddress(), client.getLocalAddress());
            } catch (Exception e) {
                logger.error("服务器连接异常：", e);
            }
        }
    }

    @Override
    protected void writeListening() {
        executorService.execute(() -> {
            logger.info("控制台写入监听中...");
            while (true) {
                if (isAvailable()) {
                    try {
                        String nextLine = new Scanner(System.in).nextLine();
                        if (isMsgJson(nextLine)) {
                            sendToClient(client, nextLine);
                        }
                    } catch (Exception e) {
                        logger.error("写字板退出");
                        break;
                    }
                } else {
                    break;
                }
            }
        });
    }

    @Override
    protected void acceptListening() {
        if (isAvailable())
            readListening(this.client);

        executorService.shutdown();
    }

    private boolean isAvailable() {
        return client != null && !client.isClosed() && client.isConnected();
    }

    /**
     * 监听外部客户端的消息
     **/
    @Override
    public void readListening(Socket socket) {
        for (; ; ) {
            try {
                if (!socket.isConnected() || socket.isClosed())
                    break;

                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String msg = dataInputStream.readUTF();
                logger.info("来源IP：{}，新消息：{}", socket.getInetAddress(), msg);
            } catch (Exception e) {
                logger.error("监听服务端消息异常：", e);
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
        client.writeListening();
        client.acceptListening();
    }

}
