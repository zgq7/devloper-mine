package com.dev.mapper.repositry;

import com.dev.mapper.base.BaseRepositry;
import com.dev.model.Aopi;
import org.springframework.stereotype.Repository;

/**
 * Created by zgq7 on 2019/7/9.
 * repositry 可可直接引用basemapper
 * basemapper 无法满足的前提下，调用具体的aopmapper
 */
@Repository(AopiRepositry.PACKAGE_BEAN_NAME)
public class AopiRepositry extends BaseRepositry<Aopi> {
    public static final String PACKAGE_BEAN_NAME = "baseRepositry";

}
