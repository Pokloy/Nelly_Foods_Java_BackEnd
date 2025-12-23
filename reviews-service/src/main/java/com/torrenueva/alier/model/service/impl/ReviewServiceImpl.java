package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
	
	@Override
	public List<ReviewDto> getAllReview(){
		List<ReviewsEntity> result = reviewRepository.getAllReview();
		
		if(result == null) {
			return new ArrayList<>();
		}
		
		return result.stream()
				.map(entity -> {
					ReviewDto dto = new ReviewDto();
					dto.setReviewId(entity.getReviewId());
					dto.setProdId(entity.getProdId());
					dto.setUserId(entity.getUserId());
					dto.setStar(entity.getStar());
					dto.setTitle(entity.getTitle());
					dto.setReview(entity.getReview());
					dto.setPhoto(entity.getPhoto());
					dto.setDeleteflg(entity.isDeleteflg());					
					return dto;
				}).toList();
		
	}
	
	@Override
	public ReviewDto getSpecificReviewByProd(String prodName) {
		try {
		ProductDto productDto = productServiceClient.getProductByName(prodName);
		
        if (productDto == null) {
            throw new NoSuchElementException("Product '" + productDto + "' not found.");
        }
		
		ReviewsEntity result = reviewRepository.getSpecificReviewByProd(productDto.getProductId());
		
        if (result == null) {
            throw new NoSuchElementException("Review '" + result + "' not found.");
        }
		
		ReviewDto dto = new ReviewDto();
		dto.setReviewId(result.getReviewId());
		dto.setProdId(result.getProdId());
		dto.setUserId(result.getUserId());
		dto.setStar(result.getStar());
		dto.setTitle(result.getTitle());
		dto.setReview(result.getReview());
		dto.setPhoto(result.getPhoto());
		dto.setDeleteflg(result.isDeleteflg());
		return dto;
		
	    } catch (NoSuchElementException ex) {
	        System.err.println(ex.getMessage()); // Replace with logger in production
	        return null; // or return an empty DTO if you prefer
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log unexpected errors
	        return null;
	    }
	}
}
