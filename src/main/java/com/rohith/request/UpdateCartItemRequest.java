package com.rohith.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

	private Long cartItemId;
	private int Quantity;
	
}
