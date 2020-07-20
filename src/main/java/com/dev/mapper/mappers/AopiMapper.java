package com.dev.mapper.mappers;

import com.dev.mapper.base.BaseMapper;
import com.dev.model.Aopi;

import java.util.List;

public interface AopiMapper extends BaseMapper<Aopi> {

	List<Aopi> findAll();
}