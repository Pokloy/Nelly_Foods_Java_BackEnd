package com.torrenueva.alier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;

import com.torrenueva.alier.common.JwtUtils;
import com.torrenueva.alier.model.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto.LoginRequest loginRequest) {
        try {
            // 1. Ask the engine to check the username/password (BCrypt happens here)
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), 
                    loginRequest.password()
                )
            );

            // 2. If we reach this line, the login was successful!
            // Generate the token using the method we wrote in Step 1
            String jwt = jwtUtils.generateToken(authentication);

            // 3. Return the token to Next.js
            return ResponseEntity.ok(new LoginDto.LoginResponse(jwt));

        } catch (AuthenticationException e) {
            // 4. If login fails, return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Error: Invalid username or password");
        }
    }
}
