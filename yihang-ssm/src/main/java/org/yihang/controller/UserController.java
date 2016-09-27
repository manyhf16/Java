package org.yihang.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yihang.entity.User;

import java.util.Date;

@Controller
public class UserController {

	@RequestMapping(path = "/user", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public User user() {
		User u = new User();
		u.setId(1);
		u.setUsername("张三");
		u.setPassword("123");
		u.setUpdatetime(new Date());
		return u;
	}
	@RequestMapping(path = "/user")
	public void userView(Model model) {
		model.addAttribute("user", user());
	}

}
