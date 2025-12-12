package com.torrenueva.alier.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.torrenueva.alier.model.dao.entity.ReviewsEntity;

public interface ReviewDao extends JpaRepository<ReviewsEntity, Integer> {
	
}
