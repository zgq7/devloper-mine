package com.dev.mapper.base;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by zgq7 on 2019/6/24.
 * 让生成的mapper文件都集成该接口
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {
    //TODO
    //FIXME 该接口不可被扫描到，否则会启动报错


    @SelectProvider(type = BaseMapperTemplate.class, method = "selectMax")
    public Integer selectMax();

    class BaseMapperTemplate {
        public String selectMax(String tableName, String pp) {
            String sql = "select max(" + pp + ") from " + tableName + "";
            return sql;
        }
    }
}