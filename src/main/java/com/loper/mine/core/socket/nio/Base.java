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
     * 表示客户端或服务端退出
     **/
    final static String OVER = "over";

    /**
     * 服务端的ip和端口
     **/
    protected String ip;
    protected int port;

    /**
     * 初始化一个select用于轮询监视
     **/
    protected Selector selector;

    public Base(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 绑定端口并注册到管道
     **/
    abstract void bindAndRegister() throws IOException;

    /**
     * 监听每个key 是否可读、可写 等状态
     **/
    abstract void listener() throws IOException;

    /**
     * 程序退出
     **/
    abstract void terminal() throws IOException;


}
