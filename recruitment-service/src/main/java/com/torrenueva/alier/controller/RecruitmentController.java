package com.torrenueva.alier.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {
	@GetMapping
	public String test() {
		return "test recruitment API";
	}
}
