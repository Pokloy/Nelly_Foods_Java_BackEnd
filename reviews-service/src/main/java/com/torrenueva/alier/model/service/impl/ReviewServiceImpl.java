package com.torrenueva.alier.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.client.ProductServiceClient;
import com.torrenueva.alier.model.client.UserServiceClient;
import com.torrenueva.alier.model.dao.ReviewDao;
import com.torrenueva.alier.model.dao.entity.ReviewsEntity;
import com.torrenueva.alier.model.dto.ProductDto;
import com.torrenueva.alier.model.dto.ReviewDto;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewDao reviewRepository;
	
	@Autowired
	ProductServiceClient productServiceClient;
	
	@Autowired
	UserServiceClient userServiceClient;
	
	@Override
	public String saveReview(ReviewDto reviewDto) {
		ProductDto productDto = productServiceClient.getProductByName(reviewDto.getProductName());
		
		UserInfoDto userInfoDto = userServiceClient.getUserByEmail(reviewDto.getEmail());
		
		ReviewsEntity reviewEntity = new ReviewsEntity();

		reviewEntity.setReview(reviewDto.getReview());
		reviewEntity.setPhoto(reviewDto.getPhoto());
		reviewEntity.setUserId(userInfoDto.getUserId());
		reviewEntity.setProdId(productDto.getProductId());
		reviewEntity.setStar(reviewDto.getStar());
		reviewEntity.setTitle(reviewDto.getTitle());
		reviewEntity.setDeleteflg(false);
		
		reviewRepository.saveAndFlush(reviewEntity);
		
		return "Saved Review";
	}
}
