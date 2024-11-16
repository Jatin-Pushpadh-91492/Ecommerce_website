package com.jatin.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
	
private Long id;
	
	private BigDecimal totalPrice;
	private List<OrderItemDto> orderItemList;
	private final LocalDateTime createdAt = LocalDateTime.now();
	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDto(Long id, BigDecimal totalPrice, List<OrderItemDto> orderItemList) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.orderItemList = orderItemList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderItemDto> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItemDto> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}	
}
