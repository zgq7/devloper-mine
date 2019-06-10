package com.dev.dao;

import com.dev.model.aopi;
import com.dev.model.aopiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface aopiMapper {
    long countByExample(aopiExample example);

    int deleteByExample(aopiExample example);

    int insert(aopi record);

    int insertSelective(aopi record);

    List<aopi> selectByExample(aopiExample example);

    int updateByExampleSelective(@Param("record") aopi record, @Param("example") aopiExample example);

    int updateByExample(@Param("record") aopi record, @Param("example") aopiExample example);
}