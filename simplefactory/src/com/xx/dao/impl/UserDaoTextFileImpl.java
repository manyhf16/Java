package com.xx.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xx.dao.UserDao;
import com.xx.entity.User;
import com.xx.exception.UserAlreadyExistsException;
import com.xx.exception.UserNotExistsException;
import com.xx.util.TextFileUtil;

/**
 * 基于文本文件的UserDao实现
 * 
 * @author yihang
 *
 */
public class UserDaoTextFileImpl implements UserDao {

	private static String FILECLASSPATH = "/user.txt";

	@Override
	public void add(User user) {
		Map<Integer, User> all = findAllInternal();
		if (all.containsKey(user.getId())) {
			throw new UserAlreadyExistsException();
		}
		all.put(user.getId(), user);
		writeChange(all);
	}

	@Override
	public void update(User user) {
		Map<Integer, User> all = findAllInternal();
		if (!all.containsKey(user.getId())) {
			throw new UserNotExistsException();
		}
		User old = all.get(user.getId());
		old.setName(user.getName());
		writeChange(all);
	}

	@Override
	public void delete(int id) {
		Map<Integer, User> all = findAllInternal();
		all.remove(id);
		writeChange(all);
	}

	@Override
	public User findById(int id) {
		Map<Integer, User> all = findAllInternal();
		return all.get(id);
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		list.addAll(findAllInternal().values());
		return list;
	}

	private void writeChange(Map<Integer, User> all) {
		List<String[]> lines = new ArrayList<>();
		for (User u : all.values()) {
			lines.add(new String[] { String.valueOf(u.getId()), u.getName() });
		}
		TextFileUtil.writeLines(lines, FILECLASSPATH);
	}

	private Map<Integer, User> findAllInternal() {
		List<String[]> lines = TextFileUtil.readLinesAndSplitWith(FILECLASSPATH);
		Map<Integer, User> map = new LinkedHashMap<>();
		for (String[] line : lines) {
			map.put(Integer.valueOf(line[0]), new User(line[0], line[1]));
		}
		return map;
	}

}
