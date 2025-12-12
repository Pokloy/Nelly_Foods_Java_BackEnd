package com.torrenueva.alier.model.dto;

import lombok.Data;

@Data
public class ProductDto {
	private int productId;
	
	private String name;
	
	private String[] photo;
	
	private int price;
	
	private String desc;
	
	private String specification;
	
	private String discount;
}