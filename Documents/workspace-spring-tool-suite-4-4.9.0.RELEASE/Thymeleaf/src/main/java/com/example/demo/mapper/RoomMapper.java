package com.example.demo.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.ReservationVO;
import com.example.demo.bean.RoomVO;

@Mapper
public interface RoomMapper {
	
	List<RoomVO> getRoomList(int id);
	
	int createReservation(ReservationVO r);
	
	List<ReservationVO> getReservationList(int u_id);
	
	int deleteReservation(int id);
	
	ReservationVO reservationDetail(int id);

	int updateReservation(ReservationVO r);
	
	List<ReservationVO> readReservByOrder(String orderValue);
}

