package com.loper.mine.service;

import com.loper.mine.mapper.repositry.AopiRepositry;
import com.loper.mine.model.Aopi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * Created by zgq7 on 2019/7/9.
 */

@Service(AopiService.PACKAGE_BEAN_NAME)
public class AopiService {
    public static final String PACKAGE_BEAN_NAME = "aopiService";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;
    @Resource
    private PmsTestService pmsTestService;
    @Resource
    private AopiService aopiService;

    /**
     * serviceA 加了 @Transactional
     * serviceB 加了 @Transactional
     * 最终：回滚
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            pmsTestService.insertWithTransactional();
        } catch (Exception e) {
            log.error("插入报错", e);
        }
        // 判断事务是否回滚
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            log.info("事务回滚了");
        } else {
            log.info("事务没回滚");
        }
    }

    /**
     * serviceA 加了 @Transactional
     * serviceB 没加 @Transactional，但是手动 throw e;
     * 最终：回滚
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertAA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            pmsTestService.insertWithoutTransactional();
        } catch (Exception e) {
            log.error("插入报错", e);
            throw e;
        }

    }

    /**
     * serviceA 加了 @Transactional
     * serviceB 没加 @Transactional，但是手动 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
     * 最终：回滚
     * <p>
     * 若不手动 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()，那么不会回滚
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertAAA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            pmsTestService.insertWithoutTransactional();
        } catch (Exception e) {
            log.error("插入报错", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        // 判断事务是否回滚
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            log.info("事务回滚了");
        } else {
            log.info("事务没回滚");
        }
    }

    /**
     * serviceA 加了 @Transactional
     * 调用过程中被异常被捕获，并不抛出
     * 最终：不回滚
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertAAAA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("插入报错", e);
        }
        // 判断事务是否回滚
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            log.info("事务回滚了");
        } else {
            log.info("事务没回滚");
        }
    }

    /**
     * 本类方法A 加了 @Transactional
     * 本类方法B 加了 @Transactional，异常被捕获，并不抛出
     * 最终：不回滚
     * <p>
     * 原因：调用 insert 并不会重新走代理调用（this 对象不是代理对象）
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertAAAAA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            insert();
        } catch (Exception e) {
            log.error("插入报错", e);
        }
        // 判断事务是否回滚
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            log.info("事务回滚了");
        } else {
            log.info("事务没回滚");
        }
    }

    /**
     * 本类方法A 加了 @Transactional
     * 自己注入自己，再调用本类方法B，加了 @Transactional，异常被捕获，并不抛出
     * 最终：回滚
     * <p>
     * 原因：aopiService bean 是一个代理bean，每次调用都会重新走代理调用逻辑。
     **/
    @Transactional(rollbackFor = Exception.class)
    public void insertAAAAAA() {
        Aopi aopi = new Aopi();
        aopi.setName("1");
        aopi.setAge(23);
        aopiRepositry.insert(aopi);
        try {
            aopiService.insert();
        } catch (Exception e) {
            log.error("插入报错", e);
        }
        // 判断事务是否回滚
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            log.info("事务回滚了");
        } else {
            log.info("事务没回滚");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        int i = 1 / 0;
    }


}
