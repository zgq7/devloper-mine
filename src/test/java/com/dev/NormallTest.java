package com.dev;

import com.dev.utils.TimeUtils;
import org.junit.Test;

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
    }
}
