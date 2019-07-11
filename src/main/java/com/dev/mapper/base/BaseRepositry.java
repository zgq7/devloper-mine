package com.dev.mapper.base;

import com.dev.utils.PageModel;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zgq7 on 2019/7/9.
 */
public class BaseRepositry<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    public static void parsePageModel(PageModel pageModel) {
        LinkedHashMap<String, String> orderBy = pageModel.getOrderBy();
        List<String> sorts = Lists.newLinkedList();
        orderBy.forEach((k, v) -> {
            sorts.add(String.join(k + " " + v));
        });
        PageHelper.startPage(pageModel);
    }
}
