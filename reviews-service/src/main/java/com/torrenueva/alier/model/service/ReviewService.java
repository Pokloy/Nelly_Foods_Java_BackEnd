package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.ReviewDto;

public interface ReviewService {
	public List<ReviewDto> getAllReview();
	public String saveReview(ReviewDto reviewDto);
	public ReviewDto getSpecificReviewByProd(String prodName);
	
}
