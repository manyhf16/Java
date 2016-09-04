package org.yihang.demo;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.yihang.common.jdbc.DBMapper;

public class DemoTest {
	
	DBMapper mapper = DBMapper.getInstance();

	@Test
	public void test1() {
		int insert = mapper.insert("insert into users(username,password) values(?,?)", "id", int.class, "lisi", "123");
		System.out.println(insert);
	}

	@Test
	public void test2() {
		int update = mapper.update("delete from users where id = ?", 1);
		System.out.println(update);
	}

	@Test
	public void test3() {		
		User user = mapper.queryOne(User.class, "select id, username, password from users where id = ?", 2);
		System.out.println(user);
	}

	@Test
	public void test4() {
		for (int i = 0; i < 10; i++) {
			mapper.update("insert into users(username,password) values(?,?)", "lisi" + i, "123");
		}
	}
	
	@Test
	public void test5() {
		long c = mapper.queryOne(long.class, "select count(*) from users");
		System.out.println(c);
	}
	
	@Test
	public void test6() {
		List<User> list = mapper.queryList(User.class, "select id, username, password from users");
		System.out.println(list);
	}
	
	@Test
	public void test7() {
		List<User> list = mapper.queryList(User.class, 1, 5, "select id, username, password from users");
		System.out.println(list);
	}
	
	@Test
	public void test8() {
		List<User> list = mapper.queryList(User.class, 3, 5, "select id, username, password from users");
		System.out.println(list);
	}
	
	@Test
	public void test9() {
		Map<Integer, String> map = mapper.queryIdMap(Integer.class, String.class, "select id, username from users where id < ?", 5);
		System.out.println(map);
	}
	
	@Test
	public void test10() {
		Map<Integer, User> map = mapper.queryIdMap(Integer.class, User.class, "select id, username, password from users where id < ?", 5);
		System.out.println(map);
	}

}
