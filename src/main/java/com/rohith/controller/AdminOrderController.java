package com.rohith.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.Order;
import com.rohith.model.User;
import com.rohith.service.OrderService;
import com.rohith.service.UserService;


@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/order/restaurant/{restaurantId}")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestParam(required = false) String orderStatus, @PathVariable Long restaurantId,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<Order> order = orderService.getRestaurantsOrder(restaurantId,orderStatus);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderStatus, @PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.updateOrder(orderId,orderStatus);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
}
