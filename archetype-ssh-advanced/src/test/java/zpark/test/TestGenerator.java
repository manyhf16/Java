package zpark.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.entity.City;

public class TestGenerator extends TestBasic {
	
	@Autowired
	private SessionFactory factory;
	
	@Test
	public void test() {
		initDatabase();
	}

	@Test
	public void test2() {
		City c = new City();
		c.setName("北京");
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(c);
		session.getTransaction().commit();
	}
}
