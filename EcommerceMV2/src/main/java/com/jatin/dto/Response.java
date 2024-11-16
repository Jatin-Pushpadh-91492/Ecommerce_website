package com.jatin.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private int status;
	private String message;
	private final LocalDateTime timeStamp = LocalDateTime.now();

	private String token;
	private String role;
	private String expirationTime;

	private int totalPage;
	private long totalElement;

	private AddressDto address;

	private UserDto user;
	private List<UserDto> userList;

	private CategoryDto category;
	private List<CategoryDto> categoryList;

	private ProductDto product;
	private List<ProductDto> productList;

	private OrderItemDto orderItem;
	private List<OrderItemDto> orderItemList;

	private OrderDto order;
	private List<OrderDto> orderList;

	// Private constructor
	private Response(Builder builder) {
		this.status = builder.status;
		this.message = builder.message;
		this.token = builder.token;
		this.role = builder.role;
		this.expirationTime = builder.expirationTime;
		this.totalPage = builder.totalPage;
		this.totalElement = builder.totalElement;
		this.address = builder.address;
		this.user = builder.user;
		this.userList = builder.userList;
		this.category = builder.category;
		this.categoryList = builder.categoryList;
		this.product = builder.product;
		this.productList = builder.productList;
		this.orderItem = builder.orderItem;
		this.orderItemList = builder.orderItemList;
		this.order = builder.order;
		this.orderList = builder.orderList;
	}


	public int getStatus() {
		return status; 
	}
	
	public String getMessage() { 
		return message; 
	}

	public String getToken() {
	 return token; 
	}
	
	public String getRole() {
	 return role; 
	}
	
	public String getExpirationTime() {
	 return expirationTime; 
	}
	
	public int getTotalPage() {
	 return totalPage; 
	}
	
	public long getTotalElement() {
	 return totalElement; 
	}
	
	public AddressDto getAddress() {
	 return address; 
	}
	
	public UserDto getUser() {
	 return user; 
	}
	
	public List<UserDto> getUserList() {
	 return userList; 
	}
	
	public CategoryDto getCategory() {
	 return category; 
	}
	
	public List<CategoryDto> getCategoryList() {
	 return categoryList; 
	}
	
	public ProductDto getProduct() {
	 return product; 
	}
	
	public List<ProductDto> getProductList() {
	 return productList; 
	}
	
	public OrderItemDto getOrderItem() {
	 return orderItem; 
	}
	
	public List<OrderItemDto> getOrderItemList() {
	 return orderItemList; 
	}
	
	public OrderDto getOrder() {
	 return order; 
	}
	
	public List<OrderDto> getOrderList() {
	 return orderList; 
	}
	
	public LocalDateTime getTimeStamp() {
	 return timeStamp; 
	}

	public static class Builder {
		private int status;
		private String message;
		private String token;
		private String role;
		private String expirationTime;
		private int totalPage;
		private long totalElement;
		private AddressDto address;
		private UserDto user;
		private List<UserDto> userList;
		private CategoryDto category;
		private List<CategoryDto> categoryList;
		private ProductDto product;
		private List<ProductDto> productList;
		private OrderItemDto orderItem;
		private List<OrderItemDto> orderItemList;
		private OrderDto order;
		private List<OrderDto> orderList;

		
		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder role(String role) {
			this.role = role;
			return this;
		}

		public Builder expirationTime(String expirationTime) {
			this.expirationTime = expirationTime;
			return this;
		}

		public Builder totalPage(int totalPage) {
			this.totalPage = totalPage;
			return this;
		}

		public Builder totalElement(long totalElement) {
			this.totalElement = totalElement;
			return this;
		}

		public Builder address(AddressDto address) {
			this.address = address;
			return this;
		}

		public Builder user(UserDto user) {
			this.user = user;
			return this;
		}

		public Builder userList(List<UserDto> userList) {
			this.userList = userList;
			return this;
		}

		public Builder category(CategoryDto category) {
			this.category = category;
			return this;
		}

		public Builder categoryList(List<CategoryDto> categoryList) {
			this.categoryList = categoryList;
			return this;
		}

		public Builder product(ProductDto product) {
			this.product = product;
			return this;
		}

		public Builder productList(List<ProductDto> productList) {
			this.productList = productList;
			return this;
		}

		public Builder orderItem(OrderItemDto orderItem) {
			this.orderItem = orderItem;
			return this;
		}

		public Builder orderItemList(List<OrderItemDto> orderItemList) {
			this.orderItemList = orderItemList;
			return this;
		}

		public Builder order(OrderDto order) {
			this.order = order;
			return this;
		}

		public Builder orderList(List<OrderDto> orderList) {
			this.orderList = orderList;
			return this;
		}
		public Response build() {
			return new Response(this);
		}
	}
	public static Builder builder() {
		return new Builder();
	}
}
