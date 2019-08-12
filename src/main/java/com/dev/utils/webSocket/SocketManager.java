package com.dev.utils.webSocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2019-08-12 12:08.
 *
 * @author zgq7
 */
public class SocketManager {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    /**
     * 群发消息
     **/
    public static void boardCast(MessageModel messageModel) {
        sessions.forEach(session -> {
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
        sessions.forEach(session -> {
            if (session.getId().equals(messageModel.getSessionId()))
                try {
                    session.getBasicRemote().sendText(messageModel.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotal() {
        return sessions.size();
    }

}
