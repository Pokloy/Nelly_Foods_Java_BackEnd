package com.torrenueva.alier.model.dto;

public class LoginDto {
	// What Next.js sends to Java
    // Note: We usually don't need 'role' in the request because 
    // the backend determines the role from the database.
    public record LoginRequest(String username, String password) {}

    // What Java sends back to Next.js
    public record LoginResponse(String accessToken, String type, String role) {
        // Handy constructor if you want to default to "Bearer"
        public LoginResponse(String accessToken, String role) {
            this(accessToken, "Bearer", role);
        }
    }
}
