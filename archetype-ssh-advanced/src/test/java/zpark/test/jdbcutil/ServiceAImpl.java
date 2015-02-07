package zpark.test.jdbcutil;

import zpark.ext.jdbc.MicroContrainer;

public class ServiceAImpl implements ServiceA{

	ServiceB serviceB = MicroContrainer.createProxy(new ServiceBImpl(), ServiceB.class);
	@Override	
	public Integer a() {
		System.out.println("a..............");
		Integer b = serviceB.b();
		System.out.println(b);
		return 0;
	}
	
	public static void main(String[] args) {
		ServiceA serviceA = MicroContrainer.createProxy(new ServiceAImpl(), ServiceA.class);
		int a = serviceA.a();
		System.out.println(a);
	}

}
