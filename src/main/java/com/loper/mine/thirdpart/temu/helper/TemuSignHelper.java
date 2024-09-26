package com.loper.mine.thirdpart.temu.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.loper.mine.thirdpart.temu.request.GoodsBrandGetRequest;
import com.loper.mine.thirdpart.temu.request.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemuSignHelper {

    public static void main(String[] args) {
        System.out.println("\\".replace("\\","\\n"));

        GoodsBrandGetRequest request = new GoodsBrandGetRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setBrandName("test");
        //request.setVid(1);
        request.setAccessToken("access_token_daskjdklasjkdljaskldjaklsjdklasjdklasjdklasjdklasjkldjsakldj");
        request.setAppKey("123");
        request.setTimestamp("456");
        request.setSign(null);
        request.setDataType("JSON");
        request.setVersion("V1");

        System.out.println(getSign(request, "apppppp"));

    }

    /**
     * 获取签名
     */
    public static String getSign(RequestBody requestBody, String appSecret) {
        Map<String, Object> map = ObjectMapperUtil.str2Obj(requestBody, new TypeReference<Map<String, Object>>() {
        });

        List<String> paramList = new LinkedList<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            if (!entry.getKey().equals("sign")) {
                paramList.add(entry.getKey() + ObjectMapperUtil.obj2Json(entry.getValue()));
            }
        });

        StringBuilder sb = new StringBuilder(appSecret);
        paramList.forEach(sb::append);

        String sign = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(sb.toString().getBytes(StandardCharsets.UTF_8));
            byte[] digest = md5.digest();
            StringBuilder signSB = new StringBuilder();
            for (byte b : digest) {
                signSB.append(String.format("%02x", b));
            }

            sign = signSB.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("temu 签名算法异常：", e);
        }

        return sign;
    }


    private static class ObjectMapperUtil {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        static {
            OBJECT_MAPPER
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

        private ObjectMapperUtil() {

        }

        /**
         * 对象转json字符串
         *
         * @param obj 对象
         * @param <T> 对象泛型
         * @return json字符串
         */
        public static <T> String obj2Json(T obj) {
            if (obj == null) {
                return null;
            }
            try {
                return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
            } catch (Exception e) {
                log.warn("对象解析为json字符串异常", e);
            }
            return null;
        }

        /**
         * 复杂对象反序列化
         * 使用例子List<User> list = JsonUtil.string2Obj(str, new TypeReference<List<User>>() {});
         *
         * @param str           json对象
         * @param typeReference 引用类型
         * @param <T>           返回值类型
         * @return 反序列化对象
         */
        public static <T> T str2Obj(String str, TypeReference<T> typeReference) {
            if (StringUtils.isBlank(str) || typeReference == null) {
                return null;
            }
            try {
                return OBJECT_MAPPER.readValue(str, typeReference);
            } catch (IOException e) {
                log.warn("json字符串解析为对象错误", e);
                return null;
            }
        }

        /**
         * 复杂对象反序列化
         * 使用例子List<User> list = JsonUtil.string2Obj(str, new TypeReference<List<User>>() {});
         *
         * @param object        对象
         * @param typeReference 引用类型
         * @param <T>           返回值类型
         * @return 反序列化对象
         */
        public static <T> T str2Obj(Object object, TypeReference<T> typeReference) {
            String str = obj2Json(object);
            return str2Obj(str, typeReference);
        }

    }
}
