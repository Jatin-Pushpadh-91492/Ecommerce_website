package com.jatin.service.interf;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.jatin.dto.Response;

public interface ProductService {

	Response createProduct(Long categoryId,MultipartFile image,String name,String description,BigDecimal price);
	
	Response updateProduct(Long productId,Long categoryId,MultipartFile image,String name,String description,BigDecimal price);
	
	Response deleteProduct(Long productId);
	
	Response getProductById(Long productId);
	
	Response getAllProduct();
	
	Response getProductByCategory(Long categoryId);
	
	Response searchProduct(String searchValue);
}
