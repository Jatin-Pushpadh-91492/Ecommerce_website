package com.jatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jatin.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{
	
}
