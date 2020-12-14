package com.dev.config.sprngcache3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liaonanzhou
 * @date 2020-11-19 16:06
 * @description 相当于 controller
 */
//@Component
public class BrokenComponent  {

    //@Resource
    @Autowired
    private TolenComponent tolenComponent;
    @Resource
    //@Autowired
    private TubunComponent tubunComponent;

}
