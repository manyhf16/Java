package zpark.ext.hibernate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;

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
				Object[][] parameterAnnotations = m.getParameterAnnotations();
				List<Object> normalParams = new ArrayList<Object>();
				if (m.getName().startsWith("find") && q != null && parameterAnnotations.length > 2) {					
					StringBuilder hql = new StringBuilder(128);
					hql.append(q.hql());
					StringBuilder count = new StringBuilder(128);	
					boolean paramSet = false;
					for(int i = 0; i< mi.getArguments().length; i++) {
						if(mi.getArguments()[i] instanceof QueryBuilder) {
							QueryBuilder qb = (QueryBuilder) mi.getArguments()[i];
							hql.append(" where 1=1 ");
							qb.build(hql, normalParams);	
							paramSet = true;
							break;
						}
					}
					count.append("select count(*) ").append(hql);
					// PageNo, PageSize 以参数上的注解优先, 如果参数上没有加注解，则使用方法上相应注解的值
					Integer no = q.pageNo();
					Integer size = q.pageSize();					
					for (int i = 0; i < parameterAnnotations.length; i++) {
						MethodParameter mp = new MethodParameter(m, i);
						PageNo pageNo = mp.getParameterAnnotation(PageNo.class);
						if (pageNo != null) {
							no = (Integer) mi.getArguments()[mp.getParameterIndex()];
							continue;
						}
						PageSize pageSize = mp.getParameterAnnotation(PageSize.class);
						if (pageSize != null) {
							size = (Integer) mi.getArguments()[mp.getParameterIndex()];
							continue;
						}
						OrderProperty orderProperty = mp.getParameterAnnotation(OrderProperty.class);
						if (orderProperty != null) {
							hql.append(" order by ").append(mi.getArguments()[mp.getParameterIndex()]);
							continue;
						}
						OrderDirection orderDirection = mp.getParameterAnnotation(OrderDirection.class);
						if (orderDirection != null) {
							hql.append(" ").append(mi.getArguments()[mp.getParameterIndex()]);
							continue;
						}
						if (mi.getArguments()[mp.getParameterIndex()] instanceof QueryBuilder) {
							continue;
						}
						if(!paramSet) {
							normalParams.add(mi.getArguments()[mp.getParameterIndex()]);
						}
					}
					System.out.println(hql);
					System.out.println(count);
					return target.findPage(no, size, hql.toString(), count.toString(), normalParams);
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
