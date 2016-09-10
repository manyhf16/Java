package com.xx.service;

import java.util.List;

import com.xx.entity.User;
import com.xx.exception.UserAlreadyExistsException;
import com.xx.exception.UserNotExistsException;

public interface UserService {

	List<User> findAll();

	User findById(int id);

	void delete(int id);

	void update(User user) throws UserNotExistsException;

	void add(User user) throws UserAlreadyExistsException;

}
