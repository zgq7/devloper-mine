package com.loper.mine.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 日志跟踪ID工具
 */
public class TraceIdUtil {
    public static final String TRACE_ID = "logId";
    private static final int MAX_ID_LENGTH = 10;

    private static String genTraceId() {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }

    public static void setTraceId(String traceId) {
        traceId = getTraceId() == null ? (StringUtils.isBlank(traceId) ? TraceIdUtil.genTraceId() : traceId) : getTraceId();
        MDC.put(TRACE_ID, StringUtils.substring(traceId, -MAX_ID_LENGTH));
    }

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID);
        return StringUtils.isBlank(traceId) ? TraceIdUtil.genTraceId() : traceId;
    }
}
