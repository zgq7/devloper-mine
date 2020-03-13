package com.dev.mapper.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

/**
 * @author Leethea
 * @apiNote
 * @date 2020/2/9 21:08
 **/
@RegisterMapper
public interface PmsMapper<T> {

	/**
	 * 获取该表的下一个序列号
	 *
	 * @return 序列的最新值 value is key(一般都是表名称全大写)映射的表的序列的最大值+step
	 * @author Leethea
	 * @apiNote @Options(useCache = false,flushCache = Options.FlushCachePolicy.TRUE)
	 * 是为了防止二级缓存对相同的SQL！！！
	 * @date 2019/12/23 15:10
	 **/
	@SelectProvider(type = PmsProvider.class, method = "dynamicSQL")
	@Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE)
	Integer selectNextSequence();


	/**
	 * 功能：从该表中查询某个字段的最大值
	 *
	 * @param colum 字段名
	 * @return 最大值
	 * @author Leethea
	 * @date 2020/2/9 21:07
	 **/
	@SelectProvider(type = PmsProvider.class, method = "dynamicSQL")
	@Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE)
	Object selectMax(@Param("colum") String colum);


	/**
	 * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，该表ID非自增;
	 * 如果属性值为空，将不会使用数据库的默认值，null值也会映射到记录中；
	 *
	 * @param recordList
	 * @return 插入的行数
	 * @Options(useGeneratedKeys = true) 表示执行该sql的数据库支持自动生成记录主键
	 * @author Leethea
	 */
	@InsertProvider(type = PmsProvider.class, method = "dynamicSQL")
	int insertListUseDefinedId(List<? extends T> recordList);

}
