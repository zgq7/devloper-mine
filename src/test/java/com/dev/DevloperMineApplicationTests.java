package com.dev;

import com.alibaba.fastjson.JSON;
import com.dev.config.CustomerThreadPoolManager;
import com.dev.config.LocalThreadPool;
import com.dev.mapper.base.BaseRepositry;
import com.dev.mapper.mappers.AopiMapper;
import com.dev.mapper.mappers.PmsTestMapper;
import com.dev.mapper.repositry.AopiRepositry;
import com.dev.model.Aopi;
import com.dev.model.PmsTest;
import com.dev.model.email.EmailModel;
import com.dev.service.AopiService;
import com.dev.utils.email.MailSendUtils;
import com.dev.utils.pageHelper.PageModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DevloperMineApplication.class)
public class DevloperMineApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Autowired
    private PmsTestMapper pmsTestMapper;

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
        PageModel pageModel3 = new PageModel.Instance(4, 2).newPageModel();
        List<Aopi> aopiList;
        try {
            BaseRepositry.parsePageModel(pageModel3);
            aopiList = aopiRepositry.selectAll();
            PageInfo<Aopi> pageInfo = new PageInfo<>(aopiList);
            System.out.println(JSON.toJSONString(pageInfo));
        } finally {
            PageHelper.clearPage();
        }
        //System.out.println(aopiList);
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
     * 线程测试
     **/
    @Test
    public void test07() {
        localThreadPool.execute(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                    logger.info("测试1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        localThreadPool.execute(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                    logger.info("测试2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        localThreadPool.execute(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                    logger.info("测试3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        localThreadPool.execute(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                    logger.info("测试4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        localThreadPool.execute(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                    logger.info("测试5");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        localThreadPool.execute(() -> logger.info("测试6"));
        localThreadPool.execute(() -> logger.info("测试7"));
        localThreadPool.execute(() -> logger.info("测试8"));
        BlockingQueue<Runnable> queue = localThreadPool.threadPoolExecutor.getQueue();

        logger.info("线程池核心数量：{}", localThreadPool.threadPoolExecutor.getPoolSize());
        logger.info("需要执行的任务数：{}", localThreadPool.threadPoolExecutor.getTaskCount());
        logger.info("正在执行的任务数：{}", localThreadPool.threadPoolExecutor.getActiveCount());
        logger.info("已完成任务数：{}", localThreadPool.threadPoolExecutor.getCompletedTaskCount());
        logger.info("最大任务数：{}", localThreadPool.threadPoolExecutor.getLargestPoolSize());


        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        emailModel.setRecieverEmailAddress("1140661106@qq.com");
        //emailModel.setRecieverEmailAddress("thisman@zgq7.club");

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

        mailSendUtils.sendEmailAsSysExceptionHtml(emailModel);

    }

    /**
     * 主键非自增测试
     **/
    @Test
    public void mappertest() {
        PmsTest test1 = new PmsTest(1, "1");
        PmsTest test2 = new PmsTest(2, null);
        PmsTest test3 = new PmsTest(3, "1");
        List<PmsTest> pmsTestList = Arrays.asList(test1, test2, test3);
        pmsTestMapper.insertListUseDefinedId(pmsTestList);

/*		try {
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

    }


    @Test
    public void ymlTest() {
        Example example = new Example(Aopi.class);
        example.createCriteria().andEqualTo("name", "1");
        PageHelper.startPage(2, 10);
        //List<Aopi> aopis = aopiMapper.selectByExample(example);
        List<Aopi> aopis = aopiMapper.selectAll();
        System.out.println(aopis);
    }

    @Test
    public void mapperTest() {
        logger.info("{}", pmsTestMapper.selectByIds(String.join(",", Arrays.asList("1", "2"))));
    }

    @Test
    public void test24() {
        CustomerThreadPoolManager.execute(() ->{
            throw new RuntimeException("error");
        });
    }

}