package com.dev;

import com.dev.config.LocalThreadPool;
import com.dev.mapper.base.BaseRepositry;
import com.dev.mapper.mappers.AopiMapper;
import com.dev.mapper.repositry.AopiRepositry;
import com.dev.model.Aopi;
import com.dev.model.email.EmailModel;
import com.dev.service.AopiService;
import com.dev.utils.email.MailSendUtils;
import com.dev.utils.pageHelper.PageModel;
import com.dev.utils.time.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DevloperMineApplicationTests {

    @Autowired
    private AopiMapper aopiMapper;

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;

    @Resource(name = AopiService.PACKAGE_BEAN_NAME)
    private AopiService aopiService;

    @Autowired
    private MailSendUtils mailSendUtils;

    @Autowired
    private LocalThreadPool localThreadPool;

    @Value("${spring.mail.username}")
    private String s;

    @Test
    public void contextLoads() {
        System.out.println(s);
    }

    /**
     * compere with stream.peek and stream.foreach
     *
     * @task 如何快速将list中的每个item内部属性值改变并进行其他流体操作呢？
     * 下面做个测试,如何在list中根据某个属性的最小值取出该对象
     **/
    @Test
    public void test01() {
        List<Aopi> aopiList = Lists.newArrayList();

        Aopi aopi = new Aopi("1", 1);
        Aopi aop2 = new Aopi("2", 2);
        Aopi aop3 = new Aopi("3", 3);
        Aopi aop4 = new Aopi("4", 4);

        aopiList.addAll(Arrays.asList(aopi, aop2, aop3, aop4));

        int count = aopiMapper.updateByPrimaryKey(aopi);
        System.out.println(count);

        //第一种方式
        aopiList.forEach(item -> item.setName(item.getName() + "_test"));
        System.out.println(
                aopiList.stream().min((o1, o2) -> {
                    if (Objects.equals(o1.getAge(), o2.getAge()))
                        return 0;
                    return o1.getAge() > o2.getAge() ? 1 : -1;
                }).get().toString()
        );

        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

        //第二种方式
        System.out.println(
                aopiList.stream().peek(item -> item.setName(item.getName() + "_test")).min((o1, o2) -> {
                    if (Objects.equals(o1.getAge(), o2.getAge()))
                        return 0;
                    return o1.getAge() > o2.getAge() ? 1 : -1;
                }).get().toString()
        );

        aopiList.stream().peek(item -> System.out.println(123)).collect(Collectors.toList());

    }

    @Test
    public void test02() {
        LinkedHashMap<String, String> orderBy = Maps.newLinkedHashMap();
        orderBy.put("id", "desc");
        orderBy.put("age", "asc");
        PageModel pageModel = new PageModel.Instance().orderBy(new String[]{"id", "age"}, new String[]{"desc", "asc"}).newPageModel();
        PageModel pageModel2 = new PageModel.Instance().orderBy(orderBy).newPageModel();
        List<Aopi> aopiList = null;
        try {
            BaseRepositry.parsePageModel(pageModel2);
            aopiList = aopiRepositry.selectAll();
        } finally {
            PageHelper.clearPage();
        }
        System.out.println(aopiList);
    }

    @Test
    public void test04() {
        Example example = new Example(Aopi.class);
        example.createCriteria().andEqualTo("id", 3);
        int count = aopiRepositry.updateByExample(new Aopi("1", 1), example);
        System.out.println(count);
    }

    @Test
    public void test05() {
        Thread thread = new Thread(() -> {
            try {
                int i = 1 / 0;
            } catch (RuntimeException e) {
                System.out.println(0);
            }
        });

        try {
            thread.start();
            Thread.sleep(1000);
            System.out.println(1);
        } catch (Exception e) {
            System.out.println(2);
        }
    }

    /**
     * Java Mail 文本发送
     **/
    @Test
    public void EmaiTest() {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailTheme("测试");
        emailModel.setRecieverName("测试");
        emailModel.setEmailContent("测试");
        //emailModel.setRecieverEmailAddress("3110320051@qq.com");
        //emailModel.setRecieverEmailAddress("1140661106@qq.com");
        emailModel.setRecieverEmailAddress("thisman@zgq7.club");

        mailSendUtils.sendEmailAsText(emailModel);
    }

    /**
     * Java Mail 网页发送
     **/
    @Test
    public void EmailTest2() throws MessagingException, InterruptedException {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailTheme("测试");
        emailModel.setRecieverName("董昕杰");
        emailModel.setEmailContent("");
        //emailModel.setRecieverEmailAddress("3110320051@qq.com");
        emailModel.setRecieverEmailAddress("1140661106@qq.com");

        //mailSendUtils.sendEmailAsSysExceptionHtml(emailModel);

        Thread thread1 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread2 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread3 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread4 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread5 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread6 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));
        Thread thread7 = localThreadPool.threadPoolExecutor.getThreadFactory().newThread(() -> System.out.println(1));

        thread1.wait(5000);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();

    }

}
