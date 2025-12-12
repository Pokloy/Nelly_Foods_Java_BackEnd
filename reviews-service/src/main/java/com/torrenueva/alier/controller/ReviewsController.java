package com.torrenueva.alier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.client.ProductServiceClient;
import com.torrenueva.alier.model.dto.ReviewDto;
import com.torrenueva.alier.model.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ProductServiceClient productServiceClient;
	
	@GetMapping("/test")
	public String test() {
		return "test reviews API";
	}
	
	@GetMapping
	public Respons
	
	@PostMapping
	public ResponseEntity<String> saveReview(@RequestBody ReviewDto reviewDto) {
		String result = reviewService.saveReview(reviewDto);
	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(result);		
	}
	
	
}
