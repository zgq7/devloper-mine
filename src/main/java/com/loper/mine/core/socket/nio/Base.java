package com.loper.mine.core.socket.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created on 2019-08-19 16:08.
 *
 * @author zgq7
 * @apiNote selectionKey: 注册到selector 上的 channel
 * https://blog.csdn.net/weixin_42762133/article/details/100040141
 */
public abstract class Base {

    /**
     * 初始化一个select用于轮询监视
     **/
    protected Selector selector;

    /**
     * 绑定端口并注册到管道
     *
     * @param ip   客户端/服务端 IP
     * @param port 客户端/服务端 port
     **/
    abstract void bindAndRegister(String ip, int port) throws IOException;

    /**
     * 监听每个key 是否可读、可写 等状态
     **/
    abstract void listener() throws IOException;


}
