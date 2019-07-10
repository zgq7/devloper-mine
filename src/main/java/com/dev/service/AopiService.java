package com.dev.service;

import com.dev.mapper.repositry.AopiRepositry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zgq7 on 2019/7/9.
 */

@Service(AopiService.PACKAGE_BEAN_NAME)
public class AopiService {
    public static final String PACKAGE_BEAN_NAME = "aopiService";

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;
}
