package com.jatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jatin.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

	
	
}
