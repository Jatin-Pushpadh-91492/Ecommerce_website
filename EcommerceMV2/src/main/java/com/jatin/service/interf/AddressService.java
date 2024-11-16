package com.jatin.service.interf;

import com.jatin.dto.AddressDto;
import com.jatin.dto.Response;

public interface AddressService {
	
		Response saveAndUpdateAddress(AddressDto addressDto);
	
}
