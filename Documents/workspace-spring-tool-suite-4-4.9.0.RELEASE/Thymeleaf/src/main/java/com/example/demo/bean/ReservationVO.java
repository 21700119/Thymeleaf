package com.example.demo.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVO {
	//DateTimeFormat(pattern = "yyyy-MM-dd")
	private int id;
	private int r_id;
	private int u_id;
	private String name;
	private String email;
	private String prof;
	private String department;
	private String purpose;
	private String contact;
	private String info;
	private Date r_date;
	private String start_time;
	private String end_time;
	private Date reg_date;
	private String room_name;
	
}
