package com.torrenueva.alier.model.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Table(name = "tb_recruitment")
@Data
public class RecruitmentEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "referral_id")
	private int referralId;
	
	@Column(name = "inviter_id")
	private int inviterId;
	
	@Column(name = "invited_id")
	private int invitedId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "update_date")
	private Timestamp updateDate;
}
