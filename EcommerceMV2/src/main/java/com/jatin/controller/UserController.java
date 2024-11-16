package com.jatin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.dto.Response;
import com.jatin.service.interf.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/get-all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllUsers(){
		
		return ResponseEntity.ok(userService.getAllUsers());
		
	}
	
	@GetMapping("/my-info")
	public ResponseEntity<Response> getUserInfoAndOrderHistory(){
		
		return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
		
	}
	
}
