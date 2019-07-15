package com.dev.service;

import com.dev.mapper.repositry.AopiRepositry;
import com.dev.model.Aopi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zgq7 on 2019/7/9.
 */

@Service(AopiService.PACKAGE_BEAN_NAME)
public class AopiService {
    public static final String PACKAGE_BEAN_NAME = "aopiService";

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;

    public List<Aopi> getList() {
        System.out.println(1);
        if (1 == 1)
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return aopiRepositry.selectAll();
    }

}
