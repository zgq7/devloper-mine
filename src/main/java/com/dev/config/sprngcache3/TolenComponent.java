package com.dev.config.sprngcache3;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zgq7
 * @date 2020-11-19 10:32
 * @description  相当于service
 */
@Transactional
@Component
public class TolenComponent {

    @Resource
    private TubunComponent tubunComponent;

}
