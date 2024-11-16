package com.jatin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jatin.dto.OrderItemDto;
import com.jatin.dto.OrderRequest;
import com.jatin.dto.Response;
import com.jatin.entity.Order;
import com.jatin.entity.OrderItem;
import com.jatin.entity.Product;
import com.jatin.entity.User;
import com.jatin.enums.OrderStatus;
import com.jatin.exception.NotFoundException;
import com.jatin.mapper.EntityDtoMapper;
import com.jatin.repository.OrderItemRepo;
import com.jatin.repository.OrderRepo;
import com.jatin.repository.ProductRepo;
import com.jatin.service.interf.OrderItemService;
import com.jatin.service.interf.UserService;
import com.jatin.specification.OrderItemSpecifiaction;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService{

	private final OrderRepo orderRepo;
	private final OrderItemRepo orderItemRepo;
	private final ProductRepo productRepo;
	private final UserService userService;
	private final EntityDtoMapper entityDtoMapper;
	
	
	public OrderItemServiceImpl(OrderRepo orderRepo, OrderItemRepo orderItemRepo, ProductRepo productRepo,
			UserService userService, EntityDtoMapper entityDtoMapper) {
		super();
		this.orderRepo = orderRepo;
		this.orderItemRepo = orderItemRepo;
		this.productRepo = productRepo;
		this.userService = userService;
		this.entityDtoMapper = entityDtoMapper;
	}

	@Override
	public Response placeOrder(OrderRequest orderRequest) {
	    User user = userService.getLoginUser();
	    
	    List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
	        Product product = productRepo.findById(orderItemRequest.getProductId())
	                .orElseThrow(() -> new NotFoundException("Product not found"));
	    
	        OrderItem orderItem = new OrderItem();
	        orderItem.setProduct(product);
	        orderItem.setQuantity(orderItemRequest.getQuantity());
	        orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
	        orderItem.setStatus(OrderStatus.PENDING);
	        orderItem.setUser(user);
	        return orderItem;
	    }).collect(Collectors.toList());

	    BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
	            ? orderRequest.getTotalPrice()
	            : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

	    Order order = new Order();
	    order.setOrderItemList(orderItems);
	    order.setTotalPrice(totalPrice);
	    
	    orderItems.forEach(orderItem -> orderItem.setOrder(order));
	    
	    orderRepo.save(order);
	    
	    List<OrderItemDto> orderItemDtos = orderItems.stream()
	            .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
	            .collect(Collectors.toList());
	    
	    return Response.builder()
	            .status(200)
	            .message("Order successfully placed")
	            .orderItemList(orderItemDtos)
	            .build();
	}

	@Override
	public Response updateOrderItemStatus(Long orderItemId, String status) {
		
		OrderItem orderItem = orderItemRepo.findById(orderItemId)
				.orElseThrow(()->new NotFoundException("Order item not found"));
	orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
	orderItemRepo.save(orderItem);
	return Response.builder()
			.status(200)
			.message("Order status updated successfully")
			.build();
	
		
	}

	@Override
	public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
	    Specification<OrderItem> spec = Specification.where(OrderItemSpecifiaction.hasStatus(status))
	            .and(OrderItemSpecifiaction.createdBetween(startDate, endDate))
	            .and(OrderItemSpecifiaction.hasItemId(itemId));
	    Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec, pageable);

	    if (orderItemPage.isEmpty()) {
	        throw new NotFoundException("No orders found");
	    }

	    List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream()
	            .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
	            .collect(Collectors.toList());

	    return Response.builder()
	            .status(200)
	            .orderItemList(orderItemDtos)
	            .totalPage(orderItemPage.getTotalPages())
	            .totalElement(orderItemPage.getTotalElements())
	            .build();
	}

	
	
}
