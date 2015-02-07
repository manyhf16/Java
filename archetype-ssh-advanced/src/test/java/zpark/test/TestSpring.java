package zpark.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;

import zpark.service.SampleService;
import zpark.service.impl.SampleServiceImpl;

public class TestSpring {
	
	@Test
	public void test1() {
		ProxyFactoryBean factory = new ProxyFactoryBean();
		factory.addInterface(SampleService.class);
		factory.setTarget(new SampleServiceImpl());
//		factory.addAdvice(new MethodInterceptor() {
//			
//			@Override
//			public Object invoke(MethodInvocation invocation) throws Throwable {
//				System.out.println("before...");
//				return invocation.proceed();
//			}
//		});
		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
		advisor.setExpression("execution(* zpark.service.impl.*.*(..))");
		advisor.setAdvice(new MethodInterceptor() {
			
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("before...");
				Object o = invocation.proceed();
				System.out.println("after...");
				return o;
			}
		});
		factory.addAdvisor(advisor);
		
		
		SampleService ss = (SampleService) factory.getObject();
		System.out.println(ss.findAll());
//		factory.addAdvisor(new AspectJPointcutAdvisor(advice));
	}
	
	@Test
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		final byte[] clazz = sun.misc.ProxyGenerator.generateProxyClass("Proxy$1", new Class[]{SampleService.class});
		final SampleServiceImpl target = new SampleServiceImpl();
		ClassLoader cl = new ClassLoader(Thread.currentThread().getContextClassLoader()) {
			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				System.out.println(name);
				return defineClass(name, clazz, 0, clazz.length);
			}
		};
		Class<?> c  = cl.loadClass("Proxy$1");
		System.out.println(c);
		SampleService o = (SampleService) c.getConstructor(InvocationHandler.class).newInstance(new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("before.......");
				return method.invoke(target, args);
			}
		});
		o.findAll();
	}

}
