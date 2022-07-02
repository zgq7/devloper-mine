package com.loper.mine.core.socket.aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.CompletionHandler;

/**
 * @author liaonanzhou
 * @date 2022/6/21 14:49
 * @description
 **/
public class WriteCompletionHandler implements CompletionHandler<Integer, String> {
    private final static Logger logger = LoggerFactory.getLogger(WriteCompletionHandler.class);

    @Override
    public void completed(Integer result, String attachment) {
        logger.info("消息发送成功，消息大小：{}", result);
    }

    @Override
    public void failed(Throwable exc, String attachment) {
        logger.error("消息发送失败：", exc);
    }
}