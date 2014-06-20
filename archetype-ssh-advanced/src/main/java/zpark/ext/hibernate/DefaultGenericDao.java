package zpark.ext.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import zpark.ext.query.Page;

/**
 * 泛型dao 实现
 * 
 * @author yihang
 * 
 * @param <T>
 *            实体类型
 * @param <ID>
 *            实体主键类型
 */
public class DefaultGenericDao<T, ID extends Serializable> implements GenericDao<T, ID> {

	private Class<?> entityClass;
	private Class<?> idClass;
	private HibernateTemplateExt template;

	protected Class<?> getEntityClass() {
		return entityClass;
	}

	protected Class<?> getIdClass() {
		return idClass;
	}

	public DefaultGenericDao(Class<?> entityClass, Class<?> idClass, HibernateTemplateExt template) {
		this.entityClass = entityClass;
		this.idClass = idClass;
		this.template = template;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID save(T entity) {
		return (ID) template.save(entity);
	}

	@Override
	public void update(T entity) {
		template.update(entity);
	}

	@Override
	public void delete(ID id) {
		Object entity = template.get(entityClass, id);
		if (entity != null) {
			template.delete(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOne(ID id) {
		return (T) template.get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return template.findByCriteria(DetachedCriteria.forClass(entityClass));
	}
	
	@Override
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, Object... objects) {
		Page<T> page = new Page<T>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<T> list = template.findPage(pageNo, pageSize, hql, objects);
		page.setList(list);
		Long total = template.findOne(Long.class, counthql, objects);
		page.setTotal(total.intValue());
		return page;
	}
	
	@Override
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, List<Object> objects) {
		Page<T> page = new Page<T>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<T> list = template.findPage(pageNo, pageSize, hql, objects);
		page.setList(list);
		Long total = template.findOne(Long.class, counthql, objects);
		page.setTotal(total.intValue());
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findOne(final String hql, final Object... objects) {
		return (T) template.findOne(entityClass, hql, objects);
	}
	
}
