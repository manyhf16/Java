package org.yihang.service;

import java.util.Map;

import org.yihang.entity.User;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface UserService {
	
	public int insert(User user);

	public int update(User user);

	public int delete(int id);

	public User findById(int id);

	public PageList<User> findByPage(PageBounds pb);
	
	public PageList<User> findByCondition(Map<String, Object> condition, PageBounds pb);

}
