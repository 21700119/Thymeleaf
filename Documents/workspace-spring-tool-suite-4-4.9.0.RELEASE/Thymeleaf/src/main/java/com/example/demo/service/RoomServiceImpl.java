package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.ReservationVO;
import com.example.demo.bean.RoomVO;
import com.example.demo.mapper.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	RoomMapper roommapper;
	
	@Override
	public List<RoomVO> getRoomList(int id){
		return roommapper.getRoomList(id);
	}
	
	@Override
	public int createReservation(ReservationVO r) {
		return roommapper.createReservation(r);
	}
	
	@Override
	public List<ReservationVO> getReservationList(int u_id){
		return roommapper.getReservationList(u_id);
	}
	
	@Override
	public int deleteReservation(int id) {
		return roommapper.deleteReservation(id);
	}
	
	@Override
	public ReservationVO reservationDetail(int id) {
		return roommapper.reservationDetail(id);
	}
	
	@Override
	public int updateReservation(ReservationVO r) {
		return roommapper.updateReservation(r);
	}
	
	@Override
	public List<ReservationVO> readReservByOrder(String orderValue){
		return roommapper.readReservByOrder(orderValue);
	}

}
