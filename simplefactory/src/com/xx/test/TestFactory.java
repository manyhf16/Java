package com.xx.test;

import org.junit.Test;

import com.xx.dao.UserDao;
import com.xx.util.Factory;

public class TestFactory {

	/**
	 * 测试工厂类： 使用《接口+工厂+配置文件》实现三层之间解耦
	 */
	@Test
	public void test1() {
		UserDao dao = Factory.getBean(UserDao.class);
		System.out.println(dao.getClass());
	}

}
