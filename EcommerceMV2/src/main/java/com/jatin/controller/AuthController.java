package com.jatin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.dto.LoginRequest;
import com.jatin.dto.Response;
import com.jatin.dto.UserDto;
import com.jatin.service.interf.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest){
		return ResponseEntity.ok(userService.registerUser(registrationRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(userService.loginUser(loginRequest));
	}
}
