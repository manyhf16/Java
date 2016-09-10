package com.xx.service.impl;

import java.util.List;

import com.xx.dao.UserDao;
import com.xx.entity.User;
import com.xx.exception.UserAlreadyExistsException;
import com.xx.exception.UserNotExistsException;
import com.xx.service.UserService;
import com.xx.util.Factory;

/**
 * 对于UserDao的使用者UserServiceImpl而言，它并不知道UserDao的具体实现类是哪个
 * 只要保证接口定义不变，那么它就不会受到UserDao实现类变动的影响
 * 
 * @author yihang
 *
 */
public class UserServiceImpl implements UserService {

	private UserDao userDao = Factory.getBean(UserDao.class);

	@Override
	public void add(User user) throws UserAlreadyExistsException {
		userDao.add(user);
	}

	@Override
	public void update(User user) throws UserNotExistsException {
		userDao.update(user);
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

}
