package com.safeHer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safeHer.dto.LoginRequest;
import com.safeHer.dto.RegisterRequest;
import com.safeHer.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	@Autowired
	private AuthService authService;
	
	
	 @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
	        String result = authService.register(
	                request.getName(),
	                request.getEmail(),
	                request.getPhone(),
	                request.getPassword()
	        );
	        return ResponseEntity.ok(result);
	    }
	 
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
	        String result = authService.login(
	                request.getEmail(),
	                request.getPassword()
	        );
	        return ResponseEntity.ok(result);
	    }
	 
	 @GetMapping("/health")
	    public ResponseEntity<String> health() {
	        return ResponseEntity.ok("Auth Service running on port 8081!");
	    }
}
