package com.loper.mine.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.TreeMap;

@Slf4j
public class SignatureUtilTest {

    @Test
    public void createSign() {
        String json = "{\n" +
                "\t\"activityId\": \"b7ae317d-851c-453a-8ada-11732c8f004d\",\n" +
                "\t\"activityName\": \"流程测试0630+001抽奖\",\n" +
                "\t\"appId\": \"329307d6-8f8b-4c49-bccf-0714c4d0928f\",\n" +
                "\t\"awardTime\": \"2022-07-01 11:27:51\",\n" +
                "\t\"componentId\": \"b7ae317d-851c-453a-8ada-11732c8f004d\",\n" +
                "\t\"ext\": \"{\\\"exchangePeriodType\\\":null,\\\"exchangeEndTime\\\":null,\\\"exchangeStartTime\\\":null,\\\"periodAfterDay\\\":null}\",\n" +
                "\t\"notifyId\": \"992391078599159808\",\n" +
                "\t\"orderId\": \"992391078599159808\",\n" +
                "\t\"prizeName\": \"50元话费\",\n" +
                "\t\"prizeNum\": 1,\n" +
                "\t\"takeChannel\": \"b7ae317d-851c-453a-8ada-11732c8f004d\",\n" +
                "\t\"takeChannelName\": \"b7ae317d-851c-453a-8ada-11732c8f004d\",\n" +
                "\t\"userInfo\": {\n" +
                "\t\t\"identityId\": \"4808015956820992\",\n" +
                "\t\t\"identityType\": \"userCode\",\n" +
                "\t\t\"identities\": {\n" +
                "\t\t\t\"userCode\": \"123456\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        TreeMap<String, Object> map = SignatureUtil.convert(json, new TypeReference<TreeMap<String, Object>>() {
        });
        map.put("mpfid", "mpf_gxpt");
        map.put("nonce", "123456");
        map.put("timestamp", "12345678901");
        map.put("secret", "ZQZ_X2iSjRvp-scajWwtClhppXc");
        System.out.println(SignatureUtil.writeValueAsString(map));

        String sha1Sign = SignatureUtil.createSHA1Sign(map);
        System.out.println(sha1Sign);

        TreeMap<String, Object> treeMap = JSONObject.parseObject(json, new com.alibaba.fastjson.TypeReference<TreeMap<String, Object>>() {
        });
        treeMap.put("mpfid", "mpf_gxpt");
        treeMap.put("nonce", "123456");
        treeMap.put("timestamp", "12345678901");
        treeMap.put("secret", "ZQZ_X2iSjRvp-scajWwtClhppXc");
        System.out.println(JSON.toJSONString(treeMap));
        String sha1Sign2 = SignatureUtil.createSHA1Sign(treeMap);
        System.out.println(sha1Sign2);

        System.out.println(sha1Sign.equals(sha1Sign2));

        log.debug("over");
        log.info("over");
        log.warn("over");
        log.error("over");
    }
}