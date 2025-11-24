package com.torrenueva.alier.model.dto;

import lombok.Data;

@Data
public class UserInfoDto {

	private int userId;
	
	private String firstName;
	
	private String middleName;
	
	private String familyName;
	
	private String email;
	
	private String phoneNumber;
	
	private String address;
	
	private String password;
	
	private String userType;

}
