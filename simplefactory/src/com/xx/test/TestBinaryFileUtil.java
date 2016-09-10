package com.xx.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.xx.entity.User;
import com.xx.util.BinaryFileUtil;

public class TestBinaryFileUtil {

	@Test
	public void test1() {
		List<User> list = BinaryFileUtil.readObjects(User.class, "/users.data");
		System.out.println(list);
	}

	@Test
	public void test2() {
		List<User> list = new ArrayList<>();
		list.add(new User("1", "张三"));
		list.add(new User("2", "李四"));
		list.add(new User("3", "王五"));
		list.add(new User("4", "赵六"));
		BinaryFileUtil.writeObjects(list, "/users.data");
	}

}
