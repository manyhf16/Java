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

	public ID save(T entity);

	public void update(T entity);

	public void delete(ID id);

	public T findOne(ID id);

	public List<T> findAll();

	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Object... objects);

	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, List<Object> objects);

	public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Map<String, Object> params);

	public T findOne(String hql, Object... objects);

	public T findOne(String hql, Map<String, Object> params);

	public <W> W findOne(Class<W> c, String hql, Object... objects);

	public <W> W findOne(Class<W> c, String hql, Map<String, Object> params);
	
	public List<T> findList(String hql, Map<String, Object> params);

}
