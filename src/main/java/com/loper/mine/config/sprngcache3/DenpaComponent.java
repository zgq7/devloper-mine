package com.loper.mine.config.sprngcache3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020-11-19 16:04
 * @description 相当于 listener
 */
@Component
public class DenpaComponent {

    @Autowired
    private TolenComponent tolenComponent;
}
