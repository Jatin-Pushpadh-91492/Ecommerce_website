package com.jatin.service.interf;

import com.jatin.dto.CategoryDto;
import com.jatin.dto.Response;

public interface CategoryService {

	Response createCategory(CategoryDto categoryRequest);
	
	Response updateCategory(Long categoryId, CategoryDto categoryRequest);
	
	Response getAllCategories();
	
	Response getCategoryById(Long categoryId);
	
	Response deleteCategory(Long categoryId);
	
}
