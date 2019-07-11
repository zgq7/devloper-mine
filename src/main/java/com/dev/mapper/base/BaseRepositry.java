package com.dev.mapper.base;

import com.dev.utils.PageModel;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
        orderBy.forEach((k, v) ->
                sorts.add(k + " " + v)
        );
        String orders = String.join(",", sorts);
        //保证线程安全
        try {
            PageHelper.startPage(pageModel.getPageNum(), pageModel.getPageSize(), orders);
        } finally {
            PageHelper.clearPage();
        }
    }
}
