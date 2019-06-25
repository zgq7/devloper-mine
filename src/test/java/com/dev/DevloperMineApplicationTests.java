package com.dev;

import com.dev.mapper.repositry.AopiMapper;
import com.dev.model.Aopi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DevloperMineApplicationTests {

    @Autowired
    private AopiMapper aopiMapper;

    @Test
    public void contextLoads() {
        Example example = new Example(Aopi.class);
        List<Aopi> aopiList = aopiMapper.selectByExample(example);
        System.out.println(aopiList);
    }

}
