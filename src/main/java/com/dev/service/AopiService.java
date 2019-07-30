package com.dev.service;

import com.dev.mapper.repositry.AopiRepositry;
import com.dev.model.Aopi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zgq7 on 2019/7/9.
 */

@Service(AopiService.PACKAGE_BEAN_NAME)
public class AopiService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String PACKAGE_BEAN_NAME = "aopiService";

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;

    public List<Aopi> getAopList() {
        return aopiRepositry.selectAll();
    }

}
