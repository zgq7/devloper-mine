package com.loper.mine.service;

import com.loper.mine.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liaonanzhou
 * @date 2021/8/31 14:05
 * @description
 **/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@Slf4j
public class AopiServiceTest {

    @Resource
    private ProxyService proxyService;
    @Resource
    private AopiService aopiService;

    @Test
    public void transactionalTestA() {
        aopiService.insertA();
    }

    @Test
    public void transactionalTestAA() {
        aopiService.insertAA();
    }

    @Test
    public void transactionalTestAAA() {
        aopiService.insertAAA();
    }

    @Test
    public void transactionalTestAAAA() {
        aopiService.insertAAAA();
    }

    @Test
    public void transactionalTestAAAAA() {
        aopiService.insertAAAAA();
    }

    @Test
    public void transactionalTestAAAAAA() {
        aopiService.insertAAAAAA();
        log.info("1");
    }

    @Test
    public void test(){
        proxyService.insert();
    }


}