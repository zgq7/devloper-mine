package com.loper.mine.core.socket.io;

import com.alibaba.fastjson.JSON;
import com.loper.mine.core.socket.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created on 2019-08-14 16:57.
 *
 * @author zgq7
 */
public abstract class Base {

    private final Logger logger = LoggerFactory.getLogger(Base.class);

    /**
     * Socket 实例初始化
     **/
    protected abstract void init();

    /**
     * 监听服务端控制台的输入
     **/
    protected abstract void writeListening();

    /**
     * 监听外部客户端的消息
     **/
    protected abstract void readListening(Socket socket);

    /**
     * 监听外部客户端连接
     **/
    protected abstract void acceptListening();

    /**
     * 发送给客户端消息
     * 1：从服务端控制条输入的数据
     * 2：客户端A 发送给 客户端B
     **/
    protected void sendToClient(Socket socket, String message) {
        if (socket != null) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(message);
            } catch (Exception e) {
                logger.error("消息发送失败");
            }
        }
    }

    protected String getSocketKey(Socket socket){
        return socket.getInetAddress().getHostAddress();
    }

    protected boolean isMsgJson(String jsonStr) {
        try {
            if (JSON.parseObject(jsonStr, MessageEntity.class) != null)
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
