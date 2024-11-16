package com.jatin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jatin.dto.ProductDto;
import com.jatin.dto.Response;
import com.jatin.entity.Category;
import com.jatin.entity.Product;
import com.jatin.exception.NotFoundException;
import com.jatin.mapper.EntityDtoMapper;
import com.jatin.repository.CategoryRepo;
import com.jatin.repository.ProductRepo;
import com.jatin.service.AwsS3Service;
import com.jatin.service.interf.ProductService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	private final ProductRepo productRepo;
	private final CategoryRepo categoryRepo;
	private final EntityDtoMapper entityDtoMapper;
	private final AwsS3Service awsS3Service;
	
	
	
	public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, EntityDtoMapper entityDtoMapper,
			AwsS3Service awsS3Service) {
		super();
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
		this.entityDtoMapper = entityDtoMapper;
		this.awsS3Service = awsS3Service;
	}

	@Override
	public Response createProduct(Long categoryId, MultipartFile image, String name, String description,
			BigDecimal price) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("category not found"));
		String productImageUrl = awsS3Service.saveImageToS3(image);
		
		Product product = new Product();
		product.setCategory(category);
		product.setPrice(price);
		product.setName(name);
		product.setDescription(description);
		product.setImageUrl(productImageUrl);
		
		productRepo.save(product);
		
		return Response.builder()
				.status(200)
				.message("Product successfully created")
				.build();
	}

	@Override
	public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description,
			BigDecimal price) {
		
		Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("product not found"));
		
		Category category = null;
		String productImageUrl = null;
		
		if(categoryId != null) {
			category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("category not found"));
		}
		
		if(image != null && !image.isEmpty()) {
			productImageUrl = awsS3Service.saveImageToS3(image);
		}
		if(category != null) product.setCategory(category);
		if(name != null) product.setName(name);
		if(price != null) product.setPrice(price);	
		if(description != null) product.setDescription(description);
		if(productImageUrl != null) product.setImageUrl(productImageUrl);
		
		
		productRepo.save(product);
		return Response.builder()
				.status(200)
				.message("product updated successfully")
				.build();
	}

	@Override
	public Response deleteProduct(Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("product not found"));
		productRepo.delete(product);
		
		return Response.builder()
				.status(200)
				.message("product deleted successfully")
				.build();
		
	}

	@Override
	public Response getProductById(Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("product not found"));
		ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
		
		return Response.builder()
				.status(200)
				.product(productDto)
				.build();
	}

	@Override
	public Response getAllProduct() {
		List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
				.stream()
				.map(entityDtoMapper::mapProductToDtoBasic)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.productList(productList)
				.build();
	}

	@Override
	public Response getProductByCategory(Long categoryId) {
		List<Product> products = productRepo.findByCategoryId(categoryId);
		if(products.isEmpty()) {
			throw new NotFoundException("No product found for this category");
		}
		
		List<ProductDto> productDtoList = products.stream()
				.map(entityDtoMapper::mapProductToDtoBasic)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.productList(productDtoList)
				.build();
	}

	@Override
	public Response searchProduct(String searchValue) {
		List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
		if(products.isEmpty()) {
			throw new NotFoundException("No product found");
		}
		
		List<ProductDto> productDtoList = products.stream()
				.map(entityDtoMapper::mapProductToDtoBasic)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.productList(productDtoList)
				.build();
	}

}
