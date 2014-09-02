package zpark.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class Advice2 implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("advice2");
		
	}

}
