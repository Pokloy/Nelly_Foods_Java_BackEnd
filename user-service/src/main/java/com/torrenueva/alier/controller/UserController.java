package com.torrenueva.alier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/test")
	public String test() {
		return "test user API";
	}
	
	@GetMapping
	public ResponseEntity<List<UserInfoDto>> getAllUser(){
		List<UserInfoDto> getAllUser = userService.getAllUsers();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(getAllUser);
	}
	
	@PostMapping
	public ResponseEntity<String> savedUser(@RequestBody UserInfoDto userDto) {
	    String result = userService.saveUser(userDto);
	    return ResponseEntity
	            .status(HttpStatus.CREATED)   // 201 Created
	            .body(result);
	}
	
	@PostMapping("/find")
	public ResponseEntity<UserInfoDto> findUser(
			@RequestParam("email") String email){
		UserInfoDto result = userService.findUserByEmail(email);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UserInfoDto userDto){
		String result = userService.saveUser(userDto);
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(result);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(
			@RequestParam("email") String email) {
		String result = userService.deleteUserByEmail(email);
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(result);
	}
}
