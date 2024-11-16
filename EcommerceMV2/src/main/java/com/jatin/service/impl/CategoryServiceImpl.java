package com.jatin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jatin.dto.CategoryDto;
import com.jatin.dto.Response;
import com.jatin.entity.Category;
import com.jatin.exception.NotFoundException;
import com.jatin.mapper.EntityDtoMapper;
import com.jatin.repository.CategoryRepo;
import com.jatin.service.interf.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepo categoryRepo;
	private final EntityDtoMapper entityDtoMapper;
	
	public CategoryServiceImpl(CategoryRepo categoryRepo, EntityDtoMapper entityDtoMapper) {
		super();
		this.categoryRepo = categoryRepo;
		this.entityDtoMapper = entityDtoMapper;
	}

	@Override
	public Response createCategory(CategoryDto categoryRequest) {
		
		Category category = new Category();
		category.setName(categoryRequest.getName());
		categoryRepo.save(category);
		return Response.builder()
				.status(200)
				.message("Category created successfully")
				.build();
	}

	@Override
	public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category not found"));
		category.setName(categoryRequest.getName());
		categoryRepo.save(category);		
		
		return Response.builder()
				.status(200)
				.build();
	}

	@Override
	public Response getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoryDtoList = categories.stream()
				.map(entityDtoMapper::mapCategoryToDtoBasic)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.categoryList(categoryDtoList)
				.build();
	}

	@Override
	public Response getCategoryById(Long categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Ctaegory Not Found"));
		CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
		return Response.builder()
				.status(200)
				.category(categoryDto)
				.build();
	}

	@Override
	public Response deleteCategory(Long categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
		categoryRepo.delete(category);
		
		return Response.builder()
				.status(200)
				.message("Category was deleted successfully")
				.build();
	}
	
	

}
