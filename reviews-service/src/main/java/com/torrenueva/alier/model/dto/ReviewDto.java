package com.torrenueva.alier.model.dto;

import lombok.Data;

@Data
public class ReviewDto {
	private int reviewId;
	
	private int prodId;
	
	private int userId;

	private int star;
	
	private String title;
	
	private String review;
	
	private String[] photo;
	
	private int deleteflg;
	
	private String email;
	
	private String productName;
}
