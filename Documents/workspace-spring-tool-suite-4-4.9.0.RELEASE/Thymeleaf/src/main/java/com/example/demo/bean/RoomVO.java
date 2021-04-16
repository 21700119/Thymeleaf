package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomVO {
	private int id;
	private String name;
	private String person;
	private int projector;
	private String info;
}
