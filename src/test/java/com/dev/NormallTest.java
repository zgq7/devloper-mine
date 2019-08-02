package com.dev;

import com.alibaba.fastjson.JSON;
import com.dev.utils.time.TimeUtils;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created on 2019-07-30 10:59.
 *
 * @author zgq7
 */
public class NormallTest {

    /**
     * TimeUtils 类的功能测试
     **/
    @Test
    public void test01() {
        System.out.println(TimeUtils.getFristDateTime(2019));
        System.out.println(TimeUtils.getLastDateTime(2018));

        System.out.println(TimeUtils.TimeToStampsOfSeconds(TimeUtils.getLastDateTime(2019)));
        System.out.println(TimeUtils.TimeToStampsOfMills(TimeUtils.getLastDateTime(2019)));

        System.out.println(TimeUtils.getFristDate(2019));
        System.out.println(TimeUtils.getLastDate(2019));

        System.out.println(HttpStatus.OK);

        String i = null;
        System.out.println(JSON.toJSONString(i));
    }
}
