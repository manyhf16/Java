package org.yihang.demo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.yihang.common.jdbc.DBMapper;

public class DBMapperTest {
	
	DBMapper mapper = DBMapper.getInstance();

	@Test
	public void test1() {
		String sql = "insert into users(id,username,password,updatetime) values(users_seq.nextval,?,?,?)";
		int insert = mapper.insert(sql, "id", int.class, "lisi", "123", new Date());
		System.out.println(insert);
	}

	@Test
	public void test2() {
		String sql = "delete from users where id = ?";
		int update = mapper.update(sql, 1);
		System.out.println(update);
	}

	@Test
	public void test3() {		
		String sql = "select id, username, password from users where id = ?";
		User user = mapper.queryOne(User.class, sql, 2);
		System.out.println(user);
	}

	@Test
	public void test4() {
		String sql = "insert into users(id,username,password) values(users_seq.nextval,?,?)";
		for (int i = 0; i < 10; i++) {
			mapper.update(sql, "lisi" + i, "123");
		}
	}
	
	@Test
	public void test5() {
		String sql = "select count(*) from users";
		long c = mapper.queryOne(long.class, sql);
		System.out.println(c);
	}
	
	@Test
	public void test6() {
		String sql = "select id, username, password from users";
		List<User> list = mapper.queryList(User.class, sql);
		System.out.println(list);
	}
	
	@Test
	public void test7() {
		String sql = "select id, username, password from users";
		List<User> list = mapper.queryList(User.class, 1, 5, sql);
		System.out.println(list);
	}
	
	@Test
	public void test8() {
		String sql = "select id, username, password from users";
		List<User> list = mapper.queryList(User.class, 3, 5, sql);
		System.out.println(list);
	}
	
	@Test
	public void test9() {
		String sql = "select id, username from users where id < ?";
		Map<Integer, String> map = mapper.queryIdMap(Integer.class, String.class, sql, 5);
		System.out.println(map);
	}
	
	@Test
	public void test10() {
		String sql = "select id, username, password from users where id < ?";
		Map<Integer, User> map = mapper.queryIdMap(Integer.class, User.class, sql, 5);
		System.out.println(map);
	}
	
}
