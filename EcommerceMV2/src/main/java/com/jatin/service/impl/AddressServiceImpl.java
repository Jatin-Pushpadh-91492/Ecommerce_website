package com.jatin.service.impl;

import org.springframework.stereotype.Service;

import com.jatin.dto.AddressDto;
import com.jatin.dto.Response;
import com.jatin.entity.Address;
import com.jatin.entity.User;
import com.jatin.repository.AddressRepo;
import com.jatin.service.interf.AddressService;
import com.jatin.service.interf.UserService;

@Service
public class AddressServiceImpl implements AddressService{
	
	private final AddressRepo addressRepo;
	private final UserService userService;
	
	public AddressServiceImpl(AddressRepo addressRepo, UserService userService) {
		super();
		this.addressRepo = addressRepo;
		this.userService = userService;
	}

	public Response saveAndUpdateAddress(AddressDto addressDto) {
		
		User user = userService.getLoginUser();
		Address address = user.getAddress();
		
		if(address == null) {
			address = new Address();
			address.setUser(user);
		}
		if(addressDto.getStreet()!=null) address.setStreet(addressDto.getStreet());
		if(addressDto.getCity()!=null) address.setCity(addressDto.getCity());
		if(addressDto.getState()!=null) address.setState(addressDto.getState());
		if(addressDto.getZipCode()!=null) address.setZipCode(addressDto.getZipCode());
		if(addressDto.getCountry()!=null) address.setCountry(addressDto.getCountry());
		
		addressRepo.save(address);
		
		String message = (user.getAddress() == null) ? "Address Successfully created" : "Address Successfully updated";
		
		return Response.builder()
				.status(200)
				.message(message)
				.build();
	}
	
}
