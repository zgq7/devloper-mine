package com.loper.mine.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 日志跟踪ID工具
 */
public class LoggerIdUtil {
    public static final String LOG_ID = "loggerId";
    private static final int MAX_LOG_ID_LENGTH = 10;


    /**
     * 为当前线程随机生成一个 logger ID
     **/
    public static void random() {
        MDC.put(LOG_ID, StringUtils.substring(genTraceId(), -MAX_LOG_ID_LENGTH));
    }

    /**
     * 获取当前线程的 logger ID
     **/
    public static String getCurrentThreadTraceId() {
        String traceId = MDC.get(LOG_ID);
        return StringUtils.isBlank(traceId) ? LoggerIdUtil.genTraceId() : traceId;
    }

    /**
     * 随机生成一个 logger ID
     **/
    private static String genTraceId() {
        return RandomStringUtils.randomAlphanumeric(MAX_LOG_ID_LENGTH);
    }
}
