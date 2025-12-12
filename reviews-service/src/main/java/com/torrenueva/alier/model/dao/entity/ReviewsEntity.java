package com.torrenueva.alier.model.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_reviews")
@Data
public class ReviewsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "review_id")
	private int reviewId;
	
	@Column(name = "prod_id")
	private int prodId;
	
	@Column(name = "user_id")
	private int userId;

	@Column(name = "star")
	private int star;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "review")
	private String review;
	
	@Column(name = "photo")
	private String[] photo;
	
	@Column(name = "delete_flag")
	private boolean deleteflg;
}
