package zpark.ext.hibernate;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component
public class HibernateTemplateExt extends HibernateTemplate {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 重写了父类方法，总是启用查询缓存
	 */
	@Override
	public boolean isCacheQueries() {
		return true;
	}

	/**
	 * 查询单个对象，对应Hibernate Session.uniqueResult方法，如不存在返回null
	 * 
	 * @param clazz
	 *            期望的返回值类型
	 * @param hql
	 *            hql查询字符串
	 * @param objects
	 *            hql中的?占位符参数值
	 * @return <hr/>
	 *         例如：要查询条件id=110000，name=北京的城市个数 <br/>
	 *         <code>
	 * 	Long count = templateExt.findOne(Long.class, "select count(*) from City where id = ? and name = ?",110000, "北京");
	 * </code>
	 */
	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> clazz, final String hql, final Object... params) {
		return executeWithNativeSession(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				prepareQuery(query);
				processArrayParams(query, params);
				return (T) query.uniqueResult();
			}
		});
	}

	/**
	 * @see #findOne(Class, String, Object...)
	 */
	public <T> T findOne(Class<T> clazz, String hql, List<Object> objects) {
		return findOne(clazz, hql, objects != null ? objects.toArray(new Object[objects.size()]) : null);
	}

	/**
	 * 查询分页结果
	 * 
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            页面大小
	 * @param hql
	 *            hql查询字符串
	 * @param params
	 *            hql中的?占位符参数值
	 * @return <hr/>
	 *         例如：要查询第1页，每页10条，符合条件id=110000，name=北京的城市<br/>
	 *         <code>
	 * 	templateExt.findPage(1, 10, "from City where id = ? and name = ?",110000, "北京");
	 * </code>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findPage(final int pageNo, final int pageSize, final String hql, final Object... params) {
		return executeWithNativeSession(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				prepareQuery(query);
				processArrayParams(query, params);
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				return (List<T>) query.list();
			}			
		});
	}
	
	@SuppressWarnings({"unchecked"})
	public <T> T findOne(Class<T> clazz, final String hql, final Map<String, Object> params) {
		return executeWithNativeSession(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				prepareQuery(query);
				processMapParams(query, params);
				return (T) query.uniqueResult();
			}
		});
	}
	
	private void processArrayParams(Query query, final Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}
	
	private void processMapParams(Query query, final Map<String, Object> params) {
		if(params != null) {
			for(Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(value instanceof Collection) {
					query.setParameterList(key, (Collection<?>)value);
				} else if(value instanceof Array) {
					query.setParameterList(key, (Object[])value);
				} else {
					query.setParameter(key, value);
				}						
			}
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public <T> List<T> findPage(final int pageNo, final int pageSize, final String hql, final Map<String, Object> params) {
		return executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				prepareQuery(query);
				processMapParams(query, params);
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				return (List<T>) query.list();
			}
		});
	}
	
	@SuppressWarnings({"unchecked"})
	public <T> List<T> findList(final String hql, final Map<String, Object> params) {
		return executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				prepareQuery(query);
				processMapParams(query, params);
				return (List<T>) query.list();
			}
		});
	}

	/**
	 * @see #findPage(int, int, String, Object...)
	 */
	public <T> List<T> findPage(final int pageNo, final int pageSize, final String hql, List<Object> params) {
		return findPage(pageNo, pageSize, hql, params != null ? params.toArray(new Object[params.size()]) : null);
	}
}
