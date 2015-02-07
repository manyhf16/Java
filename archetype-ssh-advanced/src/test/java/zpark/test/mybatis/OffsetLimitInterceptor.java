package zpark.test.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;

@Intercepts(@Signature(type=StatementHandler.class, method="prepare",args={Connection.class}))
public class OffsetLimitInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();

		MetaObject metaStatementHandler = MetaObject.forObject(handler,  
				SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		System.out.println(metaStatementHandler.getOriginalObject());

		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("boundSql");
		System.out.println(boundSql.getSql());
		System.out.println(boundSql.getParameterObject());
		
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		System.out.println(rowBounds);
		
		Object o = metaStatementHandler.getValue("delegate");
		System.out.println(o);
		
//		1. // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环  
//		2.      // 可以分离出最原始的的目标类)  
//		3.      while (metaStatementHandler.hasGetter("h")) {  
//		4.          Object object = metaStatementHandler.getValue("h");  
//		5.          metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,   
//		6.          DEFAULT_OBJECT_WRAPPER_FACTORY);  
//		7.      }  
//		8.      // 分离最后一个代理对象的目标类  
//		9.      while (metaStatementHandler.hasGetter("target")) {  
//		10.          Object object = metaStatementHandler.getValue("target");  
//		11.          metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,   
//		12.          DEFAULT_OBJECT_WRAPPER_FACTORY);  
//		13.      }  


		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties);
		
	}

}
