package org.yihang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@RequestMapping("/jsp.do")
	public void jsp() {
	}
	
	@RequestMapping(value="/string.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String string() {
		System.out.println("-------------");
		return "您好";
	}

}
