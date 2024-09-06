package com.rohith.request;

import java.util.List;

import com.rohith.model.Category;
import com.rohith.model.IngrediantsItem;

import lombok.Data;

@Data
public class CreateFoodReq {

	private String name;
	
	private String description;
	
	private Long price;
	
	private Category category;
	
	private List<String> images;
	
	private Long restaurantId;
	
	private boolean isVegetarian;
	
	private boolean isSeasonal;
	
	private List<IngrediantsItem> ingrediants;
}
