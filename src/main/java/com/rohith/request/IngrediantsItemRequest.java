package com.rohith.request;

import lombok.Data;

@Data
public class IngrediantsItemRequest {

	private String name;
	
	private Long restaurantId;
	
	private Long categoryId;
}
