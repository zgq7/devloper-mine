package com.loper.mine.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class SignatureUtil {
    private final static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper
                .registerModule(new JavaTimeModule())
                // 是否需要排序
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                // 忽略空bean转json的错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 取消默认转换timestamps形式
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // 序列化的时候，过滤null属性
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 忽略在json字符串中存在，但在java对象中不存在对应属性的情况，防止错误
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 忽略空bean转json的错误
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // the parameter WRITE_DATES_AS_TIMESTAMPS tells the mapper to represent a Date as a String in JSON
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * 创建签名
     *
     * @param paramMap 参数
     * @return 签名
     */
    public static String createSHA1Sign(SortedMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> es = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : es) {
            String k = entry.getKey();
            Object valueObj = entry.getValue();
            if (valueObj != null) {
                if (!(valueObj instanceof String)) {
                    //复杂对象使用json字符串加密
                    valueObj = writeValueAsString(valueObj);
                } else {
                    valueObj = valueObj.toString();
                }
                sb.append(k).append("=").append(valueObj).append("&");
            }
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        return getSha1(params);
    }

    /**
     * 1: 如果obj是字符串，直接返回对象本身
     * 2: 如果obj是数字，则相当于String.valueOf(obj)
     * 3: 如果obj是对象，那么转成 JSON 字符串返回
     *
     * @param obj 对象
     * @return json字符串
     */
    public static <T> String writeValueAsString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("对象解析为json字符串异常", e);
        }
        return null;
    }

    /**
     * 类型转换
     *
     * @param obj           参数
     * @param typeReference 目的转换类型
     * @return typeReference 指定的类
     */
    public static <T> T convert(Object obj, TypeReference<T> typeReference) {
        if (obj == null || typeReference == null) {
            return null;
        }
        String json = obj instanceof String ? (String) obj : writeValueAsString(obj);
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.warn("json字符串解析为对象错误", e);
            return null;
        }
    }

    /**
     * @return SHA 1 算法
     */
    private static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            MessageDigest sha1MessageDigest = MessageDigest.getInstance("SHA1");
            sha1MessageDigest.update(str.getBytes(StandardCharsets.UTF_8));

            byte[] md = sha1MessageDigest.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            logger.error("getSha1 error", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
