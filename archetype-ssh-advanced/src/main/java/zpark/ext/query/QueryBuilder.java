package zpark.ext.query;

import java.util.Map;

/**
 * 用来生成动态hql 以及为hql中占位符赋值，仅支持命名占位符
 * @author yihang
 *
 */
public interface QueryBuilder {

	/** 
	 * 回调方法
	 * @param hql 要拼接的hql
	 * @param params 值的map集合：key 占位符名 value 值
	 */
	public void build(StringBuilder hql, Map<String, Object> params);

}
