package zpark.test;

import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

public abstract class TestMethodParam {
	public abstract void test2(int bb, String dd);
	public static void test1(int aa, String cc) {
		
	}
	
	// asm, aspectj
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		ParameterNameDiscoverer pmd = new LocalVariableTableParameterNameDiscoverer();
		Method m = TestMethodParam.class.getMethod("test2", int.class, String.class);
		MethodParameter mp1 = new MethodParameter(m, 0);
		
		mp1.initParameterNameDiscovery(pmd);
		System.out.println(mp1.getParameterName());
		
		MethodParameter mp2 = new MethodParameter(m, 1);
		
		mp2.initParameterNameDiscovery(pmd);
		System.out.println(mp2.getParameterName());
	}

}
