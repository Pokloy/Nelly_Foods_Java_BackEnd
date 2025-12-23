package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.torrenueva.alier.model.dao.entity.ReviewsEntity;

public interface ReviewDao extends JpaRepository<ReviewsEntity, Integer> {
	final String GET_ALL_REVIEW = " SELECT e FROM ReviewsEntity e ";
	
	@Query(value = GET_ALL_REVIEW)
	public List<ReviewsEntity> getAllReview() throws DataAccessException;
	
	final String GET_SPECIFIC_PRODUCT_BY_NAME = " SELECT e FROM ReviewsEntity e WHERE e.prodId = :prodId AND deleteflg=false ";
	
	@Query(value=GET_SPECIFIC_PRODUCT_BY_NAME)
	public ReviewsEntity getSpecificReviewByProd(@Param(value = "prodId") int prodId) throws DataAccessException;
}
