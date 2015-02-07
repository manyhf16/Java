package zpark.ext.hibernate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 泛型dao代理类工厂
 * 
 * @author yihang
 * 
 */
public class GenericDaoProxyFactoryBean implements FactoryBean<Object> {

	private static Logger logger = LoggerFactory.getLogger(GenericDaoProxyFactoryBean.class);

	private Class<?> objectClass;

	private HibernateTemplateExt template;

	public GenericDaoProxyFactoryBean(Class<?> objectClass, HibernateTemplateExt template) {
		this.objectClass = objectClass;
		this.template = template;
	}

	private static Pattern hqlPartParser = Pattern.compile("#\\{(.+?)\\}");
	private static Pattern paramPartParser = Pattern.compile(":(\\w+)");

	private final class FindInfo {
		public Integer no;
		public Integer size;
		public StringBuffer hql;
		public StringBuilder counthql;
		public ParamMap params;
	}

	public final class GenericDaoMethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {
		private final DefaultGenericDao<?, ?> target;

		public GenericDaoMethodInterceptor(DefaultGenericDao<?, ?> target) {
			this.target = target;
		}

		public FindInfo getFindInfo(Query q, Method m, Object[] arguments, boolean needCount) {
			FindInfo info = new FindInfo();
			info.no = q.pageNo();
			info.size = q.pageSize();
			info.params = new ParamMap();
			info.counthql = new StringBuilder(128);
			info.hql = new StringBuffer(256);
			StringBuilder order = new StringBuilder().append("");
			boolean orderDirectionSet = false;
			for (int i = 0; i < arguments.length; i++) {
				MethodParameter mp = new MethodParameter(m, i);
				PageNo pageNo = mp.getParameterAnnotation(PageNo.class);
				if (pageNo != null) {
					info.no = (Integer) arguments[i];
					continue;
				}
				PageSize pageSize = mp.getParameterAnnotation(PageSize.class);
				if (pageSize != null) {
					info.size = (Integer) arguments[i];
					continue;
				}
				OrderProperty orderProperty = mp.getParameterAnnotation(OrderProperty.class);
				if (orderProperty != null) {
					if (order.length() == 0) {
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
				NamedPlaceholder namedPlaceholder = mp.getParameterAnnotation(NamedPlaceholder.class);
				if (namedPlaceholder != null) {
					info.params.put(namedPlaceholder.value(), arguments[i]);
				}
			}
			if (orderDirectionSet) {
				order.deleteCharAt(order.length() - 1);
			}
			Matcher matcher = hqlPartParser.matcher(q.hql());
			while (matcher.find()) {
				String hqlpart = matcher.group(1);
				matcher.appendReplacement(info.hql, info.params.exists(hqlpart));
			}
			if (needCount) {
				info.counthql.append("select count(*) ").append(info.hql);
			}
			info.hql.append(order.toString());
			logger.debug("hql:{}", info.hql);
			if (needCount) {
				logger.debug("count-hql:{}", info.counthql);
			}
			logger.debug("params:{}", info.params);
			return info;
		}

		@Override
		public Object invoke(MethodInvocation mi) throws Throwable {
			Method m = mi.getMethod();
			Query q = m.getAnnotation(Query.class);

			if (q != null) {
				if (zpark.ext.query.Page.class.equals(m.getReturnType())) {
					Object[] arguments = mi.getArguments();
					FindInfo info = getFindInfo(q, m, arguments, true);
					return target.findPage(info.no, info.size, info.hql.toString(), info.counthql.toString(), info.params);
				} else if (java.util.List.class.equals(m.getReturnType())) {
					Object[] arguments = mi.getArguments();
					FindInfo info = getFindInfo(q, m, arguments, false);
					return target.findList(info.no, info.size, info.hql.toString(), info.params);
				} else {
					throw new GenericDaoException("Unsupport return type for query method:[" + m.getName() + "]");
				}
			} else {
				return m.invoke(target, mi.getArguments());
			}
		}
	}

	@SuppressWarnings("serial")
	private static class ParamMap extends HashMap<String, Object> {
		// 如果该占位符参数为空, 返回"", 否则返回该hql片段
		public String exists(String hqlpart) {
			Matcher m = paramPartParser.matcher(hqlpart);
			ArrayList<String> keys = new ArrayList<String>();
			int avaliableValueCount = 0;
			while (m.find()) {
				String key = m.group(1);
				keys.add(key);
				Object value = get(key);
				if (value == null || value.equals("") || value.equals(-1)) {

				} else {
					avaliableValueCount++;
				}
			}
			if (avaliableValueCount != keys.size() || avaliableValueCount == 0) {
				for (String key : keys) {
					this.remove(key);
				}
				return "";
			} else {
				return hqlpart;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObject() throws Exception {
		Class<?>[] cls = GenericTypeResolver.resolveTypeArguments(objectClass, GenericDao.class);
		final DefaultGenericDao<?, ?> target = new DefaultGenericDao(cls[0], cls[1], template);

		ProxyFactory result = new ProxyFactory();
		result.setTarget(target);
		result.setInterfaces(new Class[] { objectClass, GenericDao.class });
		result.addAdvice(new GenericDaoMethodInterceptor(target));
		return result.getProxy();
	}

	public Class<?> getObjectType() {
		return objectClass;
	}

	public boolean isSingleton() {
		return true;
	}

}