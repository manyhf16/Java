package zpark.test.jdbcutil;


public class ServiceCImpl implements ServiceC {

	@Override	
	public Integer c() {
		System.out.println("c..............");
		int i = 1/0;
		return 2;
	}

}
