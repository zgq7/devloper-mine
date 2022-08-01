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
        MDC.put(LOG_ID, StringUtils.substring(genLoggerId(), -MAX_LOG_ID_LENGTH));
    }

    /**
     * 为当前线程随机生成一个 logger ID
     **/
    public static void setLoggerId(String logId) {
        if (StringUtils.isBlank(logId) && StringUtils.isBlank(getCurrentThreadLogId()))
            random();
        else
            MDC.put(LOG_ID, StringUtils.substring(logId, -MAX_LOG_ID_LENGTH));
    }

    /**
     * 获取当前线程的 logger ID
     **/
    public static String getCurrentThreadLogId() {
        return MDC.get(LOG_ID);
    }

    /**
     * 随机生成一个 logger ID
     **/
    private static String genLoggerId() {
        return RandomStringUtils.randomAlphanumeric(MAX_LOG_ID_LENGTH);
    }
}
