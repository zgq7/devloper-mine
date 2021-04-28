package com.loper.mine.utils.websocket;

import com.alibaba.fastjson.JSONObject;
import com.loper.mine.config.LocalThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created on 2019-08-12 12:00.
 *
 * @author zgq7
 */
@ServerEndpoint(value = "/ws/wserver/{i}")
public class SocketServer {

    private static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private Session session;

    @Autowired
    private LocalThreadPool localThreadPool;

    @OnOpen
    public void onOpen(Session session) {
        synchronized (Thread.currentThread()) {
            this.session = session;
            SocketManager.addSession(session.getId(), session);
            logger.info("新的客户端【{}】,当前大小：{}", session.getId(), SocketManager.getTotal());
        }
    }

    @OnClose
    public void onClose(Session session) {
        SocketManager.removeSession(session);
        logger.info("客户端【{}】关闭连接,当前大小：{}", session.getId(),SocketManager.getTotal());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("客户端：【{}】有新消息：{}", session.getId(), message);
        MessageModel messageModel = JSONObject.parseObject(message, MessageModel.class);
        SocketManager.singleCast(messageModel);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("客户端：【{}】产生错误", session.getId());
        error.printStackTrace();
    }

}
