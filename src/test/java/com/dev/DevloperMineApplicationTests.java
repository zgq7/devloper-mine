package com.dev;

import com.dev.mapper.base.BaseRepositry;
import com.dev.mapper.mappers.AopiMapper;
import com.dev.mapper.repositry.AopiRepositry;
import com.dev.model.Aopi;
import com.dev.utils.PageModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DevloperMineApplicationTests {

    @Autowired
    private AopiMapper aopiMapper;

    @Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
    private AopiRepositry aopiRepositry;

    @Test
    public void contextLoads() {
        Example example = new Example(Aopi.class);
        List<Aopi> aopiList = aopiMapper.selectByExample(example);
        System.out.println(aopiList);
    }

    /**
     * compere with stream.peek and stream.foreach
     *
     * @task 如何快速将list中的每个item内部属性值改变并进行其他流体操作呢？
     * 下面做个测试,如何在list中根据某个属性的最小值取出该对象
     **/
    @Test
    public void test01() {
        List<Aopi> aopiList = Lists.newArrayList();

        Aopi aopi = new Aopi("1", 1);
        Aopi aop2 = new Aopi("2", 2);
        Aopi aop3 = new Aopi("3", 3);
        Aopi aop4 = new Aopi("4", 4);

        aopiList.addAll(Arrays.asList(aopi, aop2, aop3, aop4));

        int count = aopiMapper.updateByPrimaryKey(aopi);
        System.out.println(count);

        //第一种方式
        aopiList.forEach(item -> item.setName(item.getName() + "_test"));
        System.out.println(
                aopiList.stream().min((o1, o2) -> {
                    if (Objects.equals(o1.getAge(), o2.getAge()))
                        return 0;
                    return o1.getAge() > o2.getAge() ? 1 : -1;
                }).get().toString()
        );

        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

        //第二种方式
        System.out.println(
                aopiList.stream().peek(item -> item.setName(item.getName() + "_test")).min((o1, o2) -> {
                    if (Objects.equals(o1.getAge(), o2.getAge()))
                        return 0;
                    return o1.getAge() > o2.getAge() ? 1 : -1;
                }).get().toString()
        );

        aopiList.stream().peek(item -> System.out.println(123)).collect(Collectors.toList());

    }

    @Test
    public void test02() {
        LinkedHashMap<String, String> orderBy = Maps.newLinkedHashMap();
        orderBy.put("id", "desc");
        orderBy.put("age", "asc");
        PageModel pageModel = new PageModel.Instance().orderBy(new String[]{"id", "age"}, new String[]{"desc", "asc"}).newPageModel();
        PageModel pageModel2 = new PageModel.Instance().orderBy(orderBy).newPageModel();
        BaseRepositry.parsePageModel(pageModel2);
        List<Aopi> aopiList = aopiRepositry.selectAll();
        System.out.println(aopiList);
    }

}
