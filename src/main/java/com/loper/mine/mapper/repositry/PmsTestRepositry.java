package com.loper.mine.mapper.repositry;

import com.loper.mine.mapper.base.BaseRepositry;
import com.loper.mine.model.PmsTest;
import org.springframework.stereotype.Repository;

/**
 * Created by zgq7 on 2019/7/9.
 * repositry 可可直接引用basemapper
 * basemapper 无法满足的前提下，调用具体的aopmapper
 */
@Repository(PmsTestRepositry.PACKAGE_BEAN_NAME)
public class PmsTestRepositry extends BaseRepositry<PmsTest> {
    public static final String PACKAGE_BEAN_NAME = "pmsTestRepositry";

}
