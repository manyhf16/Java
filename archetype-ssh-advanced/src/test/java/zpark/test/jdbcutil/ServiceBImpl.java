package zpark.test.jdbcutil;

import zpark.ext.jdbc.JdbcUtil;

public class ServiceBImpl implements ServiceB {

	private ServiceC serviceC = JdbcUtil.createProxy(new ServiceCImpl(), ServiceC.class);
	
	@Override
	public Integer b() {
		System.out.println("b..............");		
		Integer c = serviceC.c();
		System.out.println(c);
		return 1;
	}

}
