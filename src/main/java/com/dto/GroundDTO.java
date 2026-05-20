package com.dto;


import lombok.Data;


@Data
public class GroundDTO {

	private String name;
	private String location;
	private String type;

	private double pricePerHour;
	private double pricePerDay;

	private String description;
}
