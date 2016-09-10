package com.xx.test;

import org.junit.Test;

import com.xx.dao.impl.UserDaoTextFileImpl;
import com.xx.entity.User;

public class TestUserDaoTextFileImpl {

	UserDaoTextFileImpl dao = new UserDaoTextFileImpl();

	@Test
	public void test1() {
		dao.add(new User(4, "张三"));
	}

	@Test
	public void test2() {
		dao.update(new User(4, "张2"));
	}

	@Test
	public void test3() {
		dao.delete(5);
	}

	@Test
	public void test4() {
		System.out.println(dao.findById(4));
	}

	@Test
	public void test5() {
		System.out.println(dao.findAll());
	}

}
