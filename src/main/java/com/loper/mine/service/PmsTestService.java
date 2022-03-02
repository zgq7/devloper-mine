package com.loper.mine.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liaonanzhou
 * @date 2021/8/31 15:02
 * @description
 **/
@Service
public class PmsTestService {

    @Transactional(rollbackFor = Exception.class)
    public void insertWithTransactional() {
        int i = 1 / 0;
    }

    public void insertWithoutTransactional() {
        int i = 1 / 0;
    }
}
