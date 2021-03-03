package com.loper.mine.mapper.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by zgq7 on 2019/6/24.
 * 让生成的mapper文件都集成该接口
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>,PmsMapper<T> {
    //TODO
    //FIXME 该接口不可被扫描到，否则会启动报错

}
