package zpark.ext.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import zpark.ext.hibernate.Page;

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
	
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, Object... objects) ;
	
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, List<Object> objects);
	
	public T findOne(final String hql, final Object... objects) ;

}
