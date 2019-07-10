package com.dev.mapper.base;

import org.springframework.beans.factory.annotation.Autowired;

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
}
