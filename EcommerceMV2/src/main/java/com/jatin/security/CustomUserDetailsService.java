package com.jatin.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jatin.entity.User;
import com.jatin.exception.NotFoundException;
import com.jatin.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	public final UserRepo userRepo;
	
	
	public CustomUserDetailsService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username)
		.orElseThrow(()-> new NotFoundException("user / Email not found"));
		return AuthUser.builder()
			.user(user)
			.build();
	}

}
