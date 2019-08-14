package com.dev.utils.webSocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

/**
 * Created on 2019-08-12 12:08.
 *
 * @author zgq7
 */
public class SocketManager {

    /**
     * id-session
     **/
    private static Map<String, Session> sessionMap = Collections.synchronizedMap(new TreeMap<>());

    /**
     * 群发消息
     **/
    public static void boardCast(MessageModel messageModel) {
        sessionMap.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(messageModel.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 单发消息
     **/
    public static void singleCast(MessageModel messageModel) {
        Optional.ofNullable(sessionMap.get(messageModel.getSessionId())).ifPresent(session -> {
            try {
                session.getBasicRemote().sendText(messageModel.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void addSession(String id, Session session) {
        sessionMap.put(id, session);
    }

    public static void removeSession(Session session) {
        sessionMap.remove(session.getId());
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotal() {
        return sessionMap.size();
    }

}
