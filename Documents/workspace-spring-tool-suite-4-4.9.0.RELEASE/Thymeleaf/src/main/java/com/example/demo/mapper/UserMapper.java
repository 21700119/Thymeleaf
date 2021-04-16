package com.example.demo.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.bean.ReservationVO;
import com.example.demo.bean.RoomVO;
import com.example.demo.bean.UserVO;

@Mapper
public interface UserMapper {
	
	Integer readUserIDByEmail(String email);

	int readIsAdminByUserID(int userID);
	
	int createUserInfo(UserVO user);
}

