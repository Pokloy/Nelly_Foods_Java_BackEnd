package com.torrenueva.alier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.service.RecruitmentService;

@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {
	@Autowired
	RecruitmentService recruiteService;
	
	@GetMapping("/test")
	public String test() {
		return "test recruitment API";
	}
	
	@GetMapping
	public ResponseEntity<List<RecruitDto>> getAllRecruit(){
		List<RecruitDto> allrecruit = recruiteService.getAllRecruitement();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(allrecruit);
	}
}
