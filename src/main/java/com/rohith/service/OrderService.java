package com.rohith.service;

import java.util.List;

import com.rohith.model.Order;
import com.rohith.model.User;
import com.rohith.request.CreateOrderRequest;

public interface OrderService {

	public Order createOrder(CreateOrderRequest req, User user) throws Exception;
	
	public Order updateOrder(Long orderId, String orderStatus) throws Exception;
	
	public void cancelOrder(Long orderId) throws Exception;
	
	public List<Order> getUserorder(Long userId) throws Exception;
	
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;
	
	public Order findOrderById(Long orderId) throws Exception;
	
}
