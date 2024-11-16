package com.jatin.service.interf;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

import com.jatin.dto.OrderRequest;
import com.jatin.dto.Response;
import com.jatin.enums.OrderStatus;

public interface OrderItemService {
	
	Response placeOrder(OrderRequest orderRequest);
	
	Response updateOrderItemStatus(Long orderItemId, String status);
	
	Response filterOrderItems(OrderStatus status,LocalDateTime startDate, LocalDateTime endDate,Long itemId, Pageable pageable);
	
}
