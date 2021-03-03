package com.loper.mine.mapper.mappers;

import com.loper.mine.mapper.base.BaseMapper;
import com.loper.mine.model.Aopi;

import java.util.List;

public interface AopiMapper extends BaseMapper<Aopi> {

	List<Aopi> findAll();
}