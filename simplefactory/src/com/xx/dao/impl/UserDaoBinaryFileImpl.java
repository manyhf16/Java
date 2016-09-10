package com.xx.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xx.dao.UserDao;
import com.xx.entity.User;
import com.xx.exception.UserAlreadyExistsException;
import com.xx.exception.UserNotExistsException;
import com.xx.util.BinaryFileUtil;

/**
 * 基于二进制文件的UserDao实现
 * 
 * @author yihang
 *
 */
public class UserDaoBinaryFileImpl implements UserDao {

	private static String FILECLASSPATH = "/user.data";

	@Override
	public void add(User user) throws UserAlreadyExistsException {
		Map<Integer, User> all = findAllInternal();
		if (all.containsKey(user.getId())) {
			throw new UserAlreadyExistsException();
		}
		all.put(user.getId(), user);
		BinaryFileUtil.writeObjects(all.values(), FILECLASSPATH);
	}

	@Override
	public void update(User user) throws UserNotExistsException {
		Map<Integer, User> all = findAllInternal();
		if (!all.containsKey(user.getId())) {
			throw new UserNotExistsException();
		}
		all.put(user.getId(), user);
		BinaryFileUtil.writeObjects(all.values(), FILECLASSPATH);
	}

	@Override
	public void delete(int id) {
		Map<Integer, User> all = findAllInternal();
		all.remove(id);
		BinaryFileUtil.writeObjects(all.values(), FILECLASSPATH);
	}

	@Override
	public User findById(int id) {
		Map<Integer, User> all = findAllInternal();
		return all.get(id);
	}

	@Override
	public List<User> findAll() {
		Map<Integer, User> all = findAllInternal();
		List<User> list = new ArrayList<>();
		list.addAll(all.values());
		return list;
	}

	private Map<Integer, User> findAllInternal() {
		List<User> list = BinaryFileUtil.readObjects(User.class, FILECLASSPATH);
		Map<Integer, User> map = new LinkedHashMap<>();
		for (User user : list) {
			map.put(user.getId(), user);
		}
		return map;
	}

}
