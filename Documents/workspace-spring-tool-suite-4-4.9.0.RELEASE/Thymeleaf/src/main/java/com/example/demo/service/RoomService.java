package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.ReservationVO;
import com.example.demo.bean.RoomVO;

public interface RoomService {
	
	List<RoomVO> getRoomList(int id);
	
	int createReservation(ReservationVO r);
	
	
	List<ReservationVO> getReservationList(int u_id);
	
	int deleteReservation(int id);
	
	ReservationVO reservationDetail(int id);
	
	int updateReservation(ReservationVO r);
	
	List<ReservationVO> readReservByOrder(String orderValue);
}
