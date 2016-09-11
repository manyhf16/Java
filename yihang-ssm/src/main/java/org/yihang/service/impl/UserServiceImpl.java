package org.yihang.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yihang.entity.User;
import org.yihang.mapper.UserMapper;
import org.yihang.service.UserService;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int insert(User user) {
		return userMapper.insert(user);
	}

	@Override
	public int update(User user) {
		return userMapper.update(user);
	}

	@Override
	public int delete(int id) {
		return userMapper.delete(id);
	}

	@Override
	public User findById(int id) {
		return userMapper.findById(id);
	}

	@Override
	public PageList<User> findByPage(PageBounds pb) {
		return userMapper.findByPage(pb);
	}

	@Override
	public PageList<User> findByCondition(Map<String, Object> condition, PageBounds pb) {
		return userMapper.findByCondition(condition, pb);
	}

}
