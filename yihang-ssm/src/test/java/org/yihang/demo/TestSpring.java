package org.yihang.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yihang.entity.User;
import org.yihang.mapper.UserMapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSpring {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserMapper mapper;

	@Autowired
	private ResourceDatabasePopulator populator;

	@Test
	public void test00() throws ScriptException, SQLException {
		populator.populate(dataSource.getConnection());
	}

	// 测试连接复用、PreparedStatement复用
	@Test
	public void test01() throws SQLException, InterruptedException {
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
		ps.setInt(1, 1);
		ps.executeQuery();
		conn.close();
		conn = dataSource.getConnection();
		ps = conn.prepareStatement("select * from users where id = ?");
		ps.setInt(1, 1);
		ps.executeQuery();
		conn.close();
	}

	// 测试mybatis新增
	@Test
	public void test02() {
		User user = new User();
		user.setUsername("zhangsan");
		user.setPassword("123");
		user.setUpdatetime(new Date());
		Assert.assertTrue(mapper.insert(user) == 1);
		Assert.assertTrue(user.getId() == 1);
	}

	// 测试mybatis修改
	@Test
	public void test03() {
		User user = new User();
		user.setId(1);
		user.setUsername("zhangsan");
		user.setPassword("456");
		user.setUpdatetime(new Date());
		Assert.assertTrue(mapper.update(user) == 1);
	}

	// 测试mybatis查询单条
	@Test
	public void test04() {
		User user = mapper.findById(1);
		Assert.assertTrue(user != null);
	}

	@Test
	public void test05() {
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("zhangsan" + i);
			user.setPassword("123");
			user.setUpdatetime(new Date());
			mapper.insert(user);
		}
	}

	// 测试mybatis分页查询
	@Test
	public void test06() {
		Assert.assertTrue(mapper.findByPage(new PageBounds(1, 5)).size() == 5);
		Assert.assertTrue(mapper.findByPage(new PageBounds(2, 5)).size() == 5);
		Assert.assertTrue(mapper.findByPage(new PageBounds(3, 5)).size() == 1);
	}
	
	@Test
	public void test07() {
		Map<String, Object> condition = new HashMap<>();
		condition.put("username", "zhangsan");
		condition.put("password", "123");
		mapper.findByCondition(condition , new PageBounds(1, 5));
	}

	// 测试mybatis删除
	@Test
	public void test08() {
		Assert.assertTrue(mapper.delete(1) == 1);
	}

}
