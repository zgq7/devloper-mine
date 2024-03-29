package com.loper.mine.mapper.base;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.loper.mine.utils.pageHelper.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zgq7 on 2019/7/9.
 */
public class BaseRepositry<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    public int insert(T record) {
        return baseMapper.insert(record);
    }

    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    public int updateByExample(T record, Example example) {
        return baseMapper.updateByExample(record, example);
    }

    public static void parsePageModel(PageModel pageModel) {
        LinkedHashMap<String, String> orderBy = pageModel.getOrderBy();
        if (orderBy == null) {
            PageHelper.startPage(pageModel.getPageNum(), pageModel.getPageSize());
        } else {
            List<String> sorts = Lists.newLinkedList();
            orderBy.forEach((k, v) ->
                    sorts.add(k + " " + v)
            );
            String orders = String.join(",", sorts);
            PageHelper.startPage(pageModel.getPageNum(), pageModel.getPageSize(), orders);
        }
    }
}
