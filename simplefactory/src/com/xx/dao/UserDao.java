package com.xx.dao;

import java.util.List;

import com.xx.entity.User;
import com.xx.exception.UserAlreadyExistsException;
import com.xx.exception.UserNotExistsException;

public interface UserDao {

	/**
	 * 添加新用户，如果用户已存在，抛异常
	 * 
	 * @param user
	 * @throws UserAlreadyExistsException
	 */
	public void add(User user) throws UserAlreadyExistsException;

	/**
	 * 修改用户，如果用户不存在，抛异常
	 * 
	 * @param user
	 * @throws UserNotExistsException
	 */
	public void update(User user) throws UserNotExistsException;

	/**
	 * 删除某id的用户，用户不存在，也不会抛异常
	 * 
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 根据id查询用户，如果用户不存在，返回null
	 * 
	 * @param id
	 * @return
	 */
	public User findById(int id);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<User> findAll();

}
