package org.yihang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yihang.entity.User;

import java.util.Date;

@Controller
public class UserController {

	@RequestMapping("/user")
	@ResponseBody
	public User user() {
		User u = new User();
		u.setId(1);
		u.setUsername("张三");
		u.setPassword("123");
		u.setUpdatetime(new Date());
		return u;
	}

}
