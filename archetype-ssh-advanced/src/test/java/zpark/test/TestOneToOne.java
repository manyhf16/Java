package zpark.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.entity.User1;
import zpark.entity.User2;
import zpark.entity.User3;
import zpark.entity.User4;
import zpark.entity.UserDetail1;
import zpark.entity.UserDetail2;
import zpark.entity.UserDetail3;
import zpark.entity.UserDetail4;

public class TestOneToOne extends TestBasic {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void vv(){
		initDatabase();
	}
	
	@Test
	public void test1() {
		System.out.println("test1.............................");
		User1 user = (User1) sessionFactory.openSession().get(User1.class, 1);
		System.out.println(user.getName());
		System.out.println(user.getDetail().getClass());
		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test2() {
		System.out.println("test2.............................");
		UserDetail1 detail = (UserDetail1) sessionFactory.openSession().get(UserDetail1.class, 10);
		System.out.println(detail.getDetail());
		System.out.println(detail.getUser().getClass());
//		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test3() {
		System.out.println("test3.............................");
		User2 user = (User2) sessionFactory.openSession().get(User2.class, 1);
		System.out.println(user.getName());
		System.out.println(user.getDetail().getClass());
		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test4() {
		System.out.println("test4.............................");
		UserDetail2 detail = (UserDetail2) sessionFactory.openSession().get(UserDetail2.class, 1);
		System.out.println(detail.getDetail());
		System.out.println(detail.getUser().getClass());
//		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test5() {
		System.out.println("test5.............................");
		User3 user = (User3) sessionFactory.openSession().get(User3.class, 1);
		System.out.println(user.getName());
		System.out.println(user.getDetail().getClass());
		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test6() {
		System.out.println("test6.............................");
		UserDetail3 detail = (UserDetail3) sessionFactory.openSession().get(UserDetail3.class, 1);
		System.out.println(detail.getDetail());
		System.out.println(detail.getUser().getClass());
//		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test7() {
		System.out.println("test7.............................");
		User4 user = (User4) sessionFactory.openSession().get(User4.class, 1);
		System.out.println(user.getName());
		System.out.println(user.getDetail().getClass());
		System.out.println(user.getDetail().getDetail());
	}
	
	@Test
	public void test8() {
		System.out.println("test8.............................");
		UserDetail4 detail = (UserDetail4) sessionFactory.openSession().get(UserDetail4.class, 10);
		System.out.println(detail.getDetail());
		System.out.println(detail.getUser().getClass());
//		System.out.println(user.getDetail().getDetail());
	}

}
