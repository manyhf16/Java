package org.yihang.demo;

import java.util.Date;

import org.junit.Test;
import org.yihang.common.jdbc.DBMapper;
import org.yihang.common.jdbc.Transactional;

public class DBMapperProxyTest {

	@Test
	public void test1() {
		UserServiceImpl target = new UserServiceImpl();
		UserService proxy = DBMapper.getInstance().createProxy(target, UserService.class);
		User user = new User();
		user.setUsername("zhangsan");
		user.setPassword("...");
		user.setUpdateTime(new Date());
		proxy.insert(user);
	}
}

interface UserService {
	public void insert(User user);
}

class UserServiceImpl implements UserService {

	DBMapper mapper = DBMapper.getInstance();

	@Override
	@Transactional
	public void insert(User user) {
		String sql = "insert into users(id,username,password,updatetime) values(users_seq.nextval,?,?,?)";
		mapper.insert(sql, "id", int.class, user.getUsername(), user.getPassword(), user.getUpdateTime());

	}

}
