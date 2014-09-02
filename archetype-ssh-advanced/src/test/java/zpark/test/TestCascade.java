package zpark.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.entity.Order5;
import zpark.entity.User5;

public class TestCascade extends TestBasic {
	
	@Test
	public void test1() {
		initDatabase();
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void test2() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
//		User5 u2 = (User5) session.get(User5.class, 11);
//		u2.setName("李四2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
//		User5 u = (User5) session.get(User5.class, 10);
//		u.getOrders().size();
//		session.delete(u);
		User5 user =  (User5) session.get(User5.class, 11);
		Order5 o = (Order5) session.get(Order5.class, 1);
		
	//	o.setUser(user);
		
		session.getTransaction().commit();
	}
}
