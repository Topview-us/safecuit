package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.device.dao.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Garson in 9:48 2018/1/12
 * Description :
 */
@RestController
public class UserCon {

	@Resource
	private UserMapper userMapper;
	@Resource
	private HttpServletRequest request;

	@RequestMapping("/name")
	public String getName(){
		return userMapper.selectUser(1);
	}

	@RequestMapping("/name2")
	public String get(){
		return request.getHeader("Origin");
	}
}
