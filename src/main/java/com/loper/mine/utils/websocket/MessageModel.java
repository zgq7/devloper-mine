package com.loper.mine.utils.websocket;

/**
 * Created on 2019-08-12 14:47.
 *
 * @author zgq7
 */
public class MessageModel {

    private String sessionId;

    private long msgId;

    private String content;

    public MessageModel(String sessionId, long msgId, String content) {
        this.sessionId = sessionId;
        this.msgId = msgId;
        this.content = content;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getMsgId() {
        return msgId;
    }

    public String getContent() {
        return content;
    }
}
