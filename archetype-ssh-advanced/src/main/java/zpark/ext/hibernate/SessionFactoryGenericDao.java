package zpark.ext.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zpark.ext.query.Page;

public class SessionFactoryGenericDao<T, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<?> entityClass;
    private Class<?> idClass;
    private SessionFactory sessionFactory;

    protected Class<?> getEntityClass() {
        return entityClass;
    }

    protected Class<?> getIdClass() {
        return idClass;
    }

    public SessionFactoryGenericDao(Class<?> entityClass, Class<?> idClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ID save(T entity) {
        return (ID) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(ID id) {
        Session session = sessionFactory.getCurrentSession();
        Object entity = session.get(entityClass, id);
        if (entity != null) {
            session.delete(entity);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findOne(ID id) {
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findList(int pageNo, int pageSize, String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setArrayParams(query, params);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findList(int pageNo, int pageSize, String hql, Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setMapParams(query, params);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    private void setMapParams(Query query, Map<String, Object> params) {
        if (params != null) {
            query.setProperties(params);
        }
    }

    private void setArrayParams(Query query, Object... params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

    @Override
    public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Object... params) {
        Page<T> page = new Page<T>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<T> list = findList(pageNo, pageSize, hql, params);
        page.setList(list);
        Long total = findOne(Long.class, counthql, params);
        page.setTotal(total.intValue());
        return page;
    }

    @Override
    public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, List<Object> params) {
        return findPage(pageNo, pageSize, hql, counthql, params != null ? params.toArray(new Object[params.size()])
                : null);
    }

    @Override
    public Page<T> findPage(int pageNo, int pageSize, String hql, String counthql, Map<String, Object> params) {
        Page<T> page = new Page<T>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<T> list = findList(pageNo, pageSize, hql, params);
        page.setList(list);
        Long total = findOne(Long.class, counthql, params);
        page.setTotal(total.intValue());
        return page;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findOne(String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setArrayParams(query, params);
        return (T) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findOne(String hql, Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setMapParams(query, params);
        return (T) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <W> W findOne(Class<W> c, String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setArrayParams(query, params);
        return (W) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <W> W findOne(Class<W> c, String hql, Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setMapParams(query, params);
        return (W) query.uniqueResult();
    }

}
