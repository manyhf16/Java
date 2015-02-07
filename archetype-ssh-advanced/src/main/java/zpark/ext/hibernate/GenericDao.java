package zpark.ext.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import zpark.ext.query.Page;

/**
 * 泛型dao接口
 * 
 * @author yihang
 * 
 * @param <T>
 *            实体类型
 * @param <ID>
 *            实体主键类型
 */
public interface GenericDao<T, ID extends Serializable> {

	// 保存实体
	public ID save(T entity); 
	
	// 更新实体
	public void update(T entity);

	// 删除实体
	public void delete(ID id);

	// 按id查询单个实体
	public T findOne(ID id);

	// 查询所有实体
	public List<T> findAll();
	
	// 根据hql查询实体, map中key: 命名占位符, value: 占位符实际值
	public List<T> findList(int pageNo, int pageSize, String hql, Map<String, Object> params);

	// 根据hql, counthql查询实体, pageNo:页号, pageSize:页面大小, 数组中包含了?占位符的实际值; 返回分页对象
	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Object... params);

	// 根据hql, counthql查询实体, pageNo:页号, pageSize:页面大小, List中包含了?占位符的实际值; 返回分页对象
	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, List<Object> params);

	// 根据hql, counthql查询实体, pageNo:页号, pageSize:页面大小, map中key: 命名占位符, value: 占位符实际值; 返回分页对象
	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Map<String, Object> params);

	// 根据hql查询单个实体
	public T findOne(String hql, Object... objects);

	// 根据hql查询单个实体
	public T findOne(String hql, Map<String, Object> params);

	// 根据hql查询单个值，注意这里用了其他类型泛型，不必和实体类型一致
	public <W> W findOne(Class<W> c, String hql, Object... objects);

	// 根据hql查询单个值，注意这里用了其他类型泛型，不必和实体类型一致
	public <W> W findOne(Class<W> c, String hql, Map<String, Object> params);

}
