package com.torrenueva.alier.model.dto;

public class LoginDto {
	// What Next.js sends you
	public record LoginRequest(String username, String password) {}

	// What you send back to Next.js
	public record LoginResponse(String accessToken, String type) {
	    public LoginResponse(String token) {
	        this(token, "Bearer");
	    }
	}
}
