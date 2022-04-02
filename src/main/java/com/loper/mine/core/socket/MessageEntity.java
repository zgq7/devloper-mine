package com.loper.mine.core.socket;


import lombok.Data;

/**
 * Created on 2019-08-15 14:02.
 *
 * @author zgq7
 */
@Data
public class MessageEntity {
    /**
     * 接收方IP
     **/
    private String ip;
    /**
     * 消息
     **/
    private String message;

}
