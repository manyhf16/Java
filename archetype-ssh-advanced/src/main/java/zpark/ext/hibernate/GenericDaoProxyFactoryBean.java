package zpark.ext.hibernate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;

import zpark.ext.annotations.NamedPlaceholder;
import zpark.ext.annotations.OrderDirection;
import zpark.ext.annotations.OrderProperty;
import zpark.ext.annotations.PageNo;
import zpark.ext.annotations.PageSize;
import zpark.ext.annotations.Query;
import zpark.ext.query.QueryBuilder;

/**
 * 泛型dao代理类工厂
 * 
 * @author yihang
 * 
 */
public class GenericDaoProxyFactoryBean implements FactoryBean<Object> {

	private Class<?> objectClass;

	private HibernateTemplateExt template;

	public GenericDaoProxyFactoryBean(Class<?> objectClass, HibernateTemplateExt template) {
		this.objectClass = objectClass;
		this.template = template;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObject() throws Exception {
		
		Class<?>[] cls = GenericTypeResolver.resolveTypeArguments(objectClass, GenericDao.class);
		final DefaultGenericDao<?, ?> target = new DefaultGenericDao(cls[0], cls[1], template);

		ProxyFactory result = new ProxyFactory();
		result.setTarget(target);
		result.setInterfaces(new Class[] { objectClass, GenericDao.class });
		result.addAdvice(new org.aopalliance.intercept.MethodInterceptor() {

			@Override
			public Object invoke(MethodInvocation mi) throws Throwable {
				Method m = mi.getMethod();
				Query q = m.getAnnotation(Query.class);
				Map<String, Object> normalParams = new HashMap<String, Object>();
				if (q != null && q.pageResult()) {
					Object[] arguments = mi.getArguments();
					StringBuilder hql = new StringBuilder(128);
					hql.append(q.hql());
					StringBuilder count = new StringBuilder(128);
					for (int i = 0; i < mi.getArguments().length; i++) {
						// 优先考虑QueryBuilder类型的参数
						if (arguments[i] instanceof QueryBuilder) {
							QueryBuilder qb = (QueryBuilder) mi.getArguments()[i];
							qb.build(hql, normalParams);
							break;
						}
					}
					count.append("select count(*) ").append(hql);
					Integer no = q.pageNo();
					Integer size = q.pageSize();
					StringBuilder order = new StringBuilder().append("");
					boolean orderDirectionSet = false;
					for (int i = 0; i < arguments.length; i++) {
						MethodParameter mp = new MethodParameter(m, i);
						PageNo pageNo = mp.getParameterAnnotation(PageNo.class);
						if (pageNo != null) {
							no = (Integer) arguments[i];
							continue;
						}
						PageSize pageSize = mp.getParameterAnnotation(PageSize.class);
						if (pageSize != null) {
							size = (Integer) arguments[i];
							continue;
						}
						OrderProperty orderProperty = mp.getParameterAnnotation(OrderProperty.class);
						if (orderProperty != null) {
							if(order.length() == 0) {
								order.append(" order by ");
							}
							order.append(arguments[i]);
							continue;
						}
						OrderDirection orderDirection = mp.getParameterAnnotation(OrderDirection.class);
						if (orderDirection != null) {
							order.append(" ").append(arguments[i]).append(",");
							orderDirectionSet = true;
							continue;
						}
						if (arguments[i] instanceof QueryBuilder) {
							continue;
						}
						NamedPlaceholder namedPlaceholder = mp.getParameterAnnotation(NamedPlaceholder.class);
						if(namedPlaceholder != null) {
							normalParams.put(namedPlaceholder.value(), arguments[i]);
						}
					}
					if(orderDirectionSet) {
						order.deleteCharAt(order.length()-1);
					}
					hql.append(order.toString());
					System.out.println(hql);
					System.out.println(count);
					System.out.println(normalParams);
					return target.findPage(no, size, hql.toString(), count.toString(), normalParams);
				} else if(q!=null && q.pageResult() == false) {
					Object[] arguments = mi.getArguments();
					StringBuilder hql = new StringBuilder(128);
					hql.append(q.hql());
					for (int i = 0; i < mi.getArguments().length; i++) {
						// 优先考虑QueryBuilder类型的参数
						if (arguments[i] instanceof QueryBuilder) {
							QueryBuilder qb = (QueryBuilder) mi.getArguments()[i];
							qb.build(hql, normalParams);
							break;
						}
					}
					StringBuilder order = new StringBuilder().append("");
					boolean orderDirectionSet = false;
					for (int i = 0; i < arguments.length; i++) {
						MethodParameter mp = new MethodParameter(m, i);
						OrderProperty orderProperty = mp.getParameterAnnotation(OrderProperty.class);
						if (orderProperty != null) {
							if(order.length() == 0) {
								order.append(" order by ");
							}
							order.append(arguments[i]);
							continue;
						}
						OrderDirection orderDirection = mp.getParameterAnnotation(OrderDirection.class);
						if (orderDirection != null) {
							order.append(" ").append(arguments[i]).append(",");
							orderDirectionSet = true;
							continue;
						}
						if (arguments[i] instanceof QueryBuilder) {
							continue;
						}
						NamedPlaceholder namedPlaceholder = mp.getParameterAnnotation(NamedPlaceholder.class);
						if(namedPlaceholder != null) {
							normalParams.put(namedPlaceholder.value(), arguments[i]);
						}
					}
					if(orderDirectionSet) {
						order.deleteCharAt(order.length()-1);
					}
					hql.append(order.toString());
					return target.findList(hql.toString(), normalParams);
				} else {
					return m.invoke(target, mi.getArguments());
				}
			}
		});
		return result.getProxy();
	}

	public Class<?> getObjectType() {
		return objectClass;
	}

	public boolean isSingleton() {
		return true;
	}

}
