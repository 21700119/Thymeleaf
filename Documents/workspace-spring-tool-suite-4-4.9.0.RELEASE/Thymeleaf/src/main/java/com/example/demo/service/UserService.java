package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.UserVO;

public interface UserService {

	Integer readUserIDByEmail(String email);

	int readIsAdminByUserID(int userID);
	
	int createUserInfo(UserVO user);

}
