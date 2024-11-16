package com.jatin.controller;

import java.math.BigDecimal;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatin.dto.Response;
import com.jatin.service.interf.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> createProduct(
		@RequestParam Long categoryId,
		@RequestParam MultipartFile image,
		@RequestParam String name,
		@RequestParam String description,
		@RequestParam BigDecimal price
	) throws InvalidCredentialsException{
		if(categoryId == null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null) {
			throw new InvalidCredentialsException("All fields are required");
		}
		return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));
	}
	
	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateProduct(
		@RequestParam Long productId,
		@RequestParam(required = false) Long categoryId,
		@RequestParam(required = false) MultipartFile image,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String description,
		@RequestParam(required = false) BigDecimal price
	){
		return ResponseEntity.ok(productService.updateProduct(productId,categoryId, image, name, description, price));
	}
	
	@DeleteMapping("/delete/{productId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteProduct(@PathVariable long productId){
		return ResponseEntity.ok(productService.deleteProduct(productId));
	}
	
	@GetMapping("/get-by-product-id/{productId}")
	public ResponseEntity<Response> getProductById(@PathVariable long productId){
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<Response> getAllProduct(){
		return ResponseEntity.ok(productService.getAllProduct());
	}
	
	@GetMapping("/get-by-category-id/{categoryId}")
	public ResponseEntity<Response> getProductsByCategory(@PathVariable long categoryId){
		return ResponseEntity.ok(productService.getProductByCategory(categoryId));
	}
	
	@GetMapping("/search")
	public ResponseEntity<Response> searchForProduct(@RequestParam String searchValue){
		return ResponseEntity.ok(productService.searchProduct(searchValue));
	}
}
