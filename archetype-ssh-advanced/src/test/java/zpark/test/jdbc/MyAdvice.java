package zpark.test.jdbc;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;

public class MyAdvice implements MethodInterceptor{

//	@Override
//	public void before(Method method, Object[] args, Object target) throws Throwable {
//		
//		throw new RuntimeException("没有权限");
//		MethodBeforeAdvice, 
//	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		System.out.println(method);
		System.out.println(method.getAnnotation(Authenticate.class));
		return null;
	}

}
