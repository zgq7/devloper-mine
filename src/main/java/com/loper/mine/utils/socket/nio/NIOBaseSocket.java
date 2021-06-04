package com.loper.mine.utils.socket.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created on 2019-08-19 16:08.
 *
 * @author zgq7
 * @apiNote selectionKey: 注册到selector 上的 channel
 */
public interface NIOBaseSocket {


    /**
     * 初始化一个nio 管道
     **/
    void init(String ip, int port) throws IOException;

    /**
     * 轮询是否有新消息
     **/
    void polling() throws IOException;

    /**
     * 建立新的管道连接
     **/
    void registry(SelectionKey key) throws IOException;

    /**
     * 读消息
     **/
    void readMsg(SelectionKey key) throws IOException;

    /**
     * 写消息
     **/
    void writeMsg(SelectionKey key) throws IOException;

}
