package com.dev.mapper.base;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author Leethea
 * @apiNote 封装的pms自定义mapper
 * @date 2020/2/14 17:08
 **/
public class PmsProvider extends MapperTemplate {

	public PmsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	/**
	 * @author Leethea
	 * @apiNote 获取 下一个序列
	 * @date 2019/12/23 15:42
	 **/
	public String selectNextSequence(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		//修改返回值类型为实体类型
		//setResultType(ms, Map.class);
		return "select nextvalfun('" +
				//获取表名
				SqlHelper.getDynamicTableName(entityClass, tableName(entityClass)) +
				"') ";
	}

	/**
	 * 功能：查询某个字段的最大值，$是通配符！
	 *
	 * @return 要被执行的sql
	 * @author Leethea
	 * @date 2020/2/9 21:04
	 **/
	public String selectMax(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		//修改返回值类型为实体类型
		//setResultType(ms, Map.class);
		StringBuilder sql = new StringBuilder();
		sql.append("select max(");
		//获取字段名
		sql.append("${colum}");
		sql.append(") ");
		sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
		return sql.toString();
	}

	/**
	 * 功能：批量插入非自增主键记录
	 *
	 * @return 被执行的sql
	 * @author Leethea
	 * @date 2020/2/14 17:07
	 **/
	public String insertListUseDefinedId(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		//开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '")
				.append(ms.getId()).append(" 方法参数为空')\"/>");
		sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass), "list[0]"));
		sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
		sql.append(" VALUES ");
		sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		//获取全部列
		Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
		//当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
		for (EntityColumn column : columnList) {
			sql.append(column.getColumnHolder("record")).append(",");
		}
		sql.append("</trim>");
		sql.append("</foreach>");

		// 反射把MappedStatement中的设置主键名
		EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

		return sql.toString();
	}


}