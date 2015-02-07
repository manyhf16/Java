package zpark.test.mybatis;

import java.util.List;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class TestMyBatis {
	
	@Test
	public void test01() {
		SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(TestMyBatis.class.getResourceAsStream("config.xml"));
		SqlSession session = sf.openSession();
		
		UserDao dao = session.getMapper(UserDao.class);
		List<User> users = dao.selectUsers();
		int a = dao.deleteUser(2);
		System.out.println(a);
//		System.out.println(users);
//		User user = new User();
////		user.setId(1);
//		user.setName("李四3");
//		dao.saveUser(user );
//		
		session.commit();
//		System.out.println(user);
		users = dao.selectUsers();
		System.out.println(users);
	}
	
	@Test
	public void test02() {
		SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(TestMyBatis.class.getResourceAsStream("config.xml"));
		for(String name :sf.getConfiguration().getCacheNames()){
			System.out.println(name);
		}
		Cache c1 = sf.getConfiguration().getCache("zpark.test.mybatis.UserDao");
		Cache c2 = sf.getConfiguration().getCache("UserDao");
		System.out.println(c1 == c2);
		SqlSession session = sf.openSession();
		
		UserDao dao = session.getMapper(UserDao.class);
		User user = dao.selectUserById(3);
		System.out.println(user);
	}

}
