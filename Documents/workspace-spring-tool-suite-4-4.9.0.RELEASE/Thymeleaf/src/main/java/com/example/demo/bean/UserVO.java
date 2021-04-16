package com.example.demo.bean;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
	private int id;
	private String name;
	private String email;
	private Date reg_date;
	private int is_admin;
}
