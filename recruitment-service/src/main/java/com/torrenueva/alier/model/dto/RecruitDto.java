package com.torrenueva.alier.model.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RecruitDto {
	private int referralId;
	
	private int inviterId;
	
	private int invitedId;
	
	private String status;
	
	private Timestamp updateDate;
}
