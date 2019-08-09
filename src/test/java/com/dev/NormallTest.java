package com.dev;

import com.dev.config.LocalThreadPool;
import com.dev.utils.zpnag.TestImpl01;
import com.dev.utils.zpnag.TestInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

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
    public void test01() throws Exception {
        //System.out.println(String.format("asst:captcha:%s", "2222"));
        //System.out.println(RandomStringUtils.random(6));

        Class klass = Class.forName(TestImpl01.PACKAGE_PATH);
        System.out.println(klass);
        Object intance = klass.newInstance();
        System.out.println(intance);

        Method method = klass.getMethod("test03", Integer.class);
        Method method2 = klass.getMethod("test2");
        System.out.println(method);

        method.invoke(intance, 1);
        method2.invoke(intance);

    }



}
