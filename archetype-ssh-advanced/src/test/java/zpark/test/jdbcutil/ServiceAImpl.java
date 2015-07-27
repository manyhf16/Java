package zpark.test.jdbcutil;

import zpark.ext.jdbc.JdbcUtil;

public class ServiceAImpl implements ServiceA{

	ServiceB serviceB = JdbcUtil.createProxy(new ServiceBImpl(), ServiceB.class);
	@Override	
	public Integer a() {
		System.out.println("a..............");
		Integer b = serviceB.b();
		System.out.println(b);
		return 0;
	}
	
	public static void main(String[] args) {
		ServiceA serviceA = JdbcUtil.createProxy(new ServiceAImpl(), ServiceA.class);
		int a = serviceA.a();
		System.out.println(a);
	}

}
