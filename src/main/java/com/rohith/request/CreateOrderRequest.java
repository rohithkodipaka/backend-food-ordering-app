package com.rohith.request;

import com.rohith.model.Address;

import lombok.Data;

@Data
public class CreateOrderRequest {
	
	private Long restaurantId;
	
	private Address deliveryAddress;

}
