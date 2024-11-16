package com.jatin.service.interf;

import com.jatin.dto.LoginRequest;
import com.jatin.dto.Response;
import com.jatin.dto.UserDto;
import com.jatin.entity.User;

public interface UserService {
	
	Response registerUser(UserDto registrationRequest);
	Response loginUser(LoginRequest loginRequest);
	Response getAllUsers();
	User getLoginUser();
	Response getUserInfoAndOrderHistory();
	
}
