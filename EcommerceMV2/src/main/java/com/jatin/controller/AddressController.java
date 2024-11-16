package com.jatin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.dto.AddressDto;
import com.jatin.dto.Response;
import com.jatin.service.interf.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto){
		return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto));
	}
	
}
