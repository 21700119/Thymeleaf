package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.UserVO;
import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper usermapper;

	@Override
	public Integer readUserIDByEmail(String email) {
		Integer userID = usermapper.readUserIDByEmail(email);
		
		return userID;
	}
	
	@Override
	public int readIsAdminByUserID(int userID) {
		return usermapper.readIsAdminByUserID(userID);
	}
	
	@Override
	public int createUserInfo(UserVO user) {
		return usermapper.createUserInfo(user);
	}
	
	
	
}
