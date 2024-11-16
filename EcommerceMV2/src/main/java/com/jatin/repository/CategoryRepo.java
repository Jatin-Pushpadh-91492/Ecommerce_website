package com.jatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jatin.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
