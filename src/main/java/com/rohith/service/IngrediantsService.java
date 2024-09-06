package com.rohith.service;

import java.util.List;

import com.rohith.model.IngrediantCategory;
import com.rohith.model.IngrediantsItem;

public interface IngrediantsService {
	
	public IngrediantCategory createIngrediantCategory(String name, Long restaurantId) throws Exception;
	
	public IngrediantCategory findIngrediantCategoryById(Long ingrediantId) throws Exception;
	
	public List<IngrediantCategory> findIngrediantCategoryByRestaurantId(Long restaurantId) throws Exception;
	
	public IngrediantsItem createIngrediantsItem(String ingrediantName, Long restaurantId, Long ingrediantCategoryId) throws Exception;
	
	public List<IngrediantsItem> findIngrediantItemsByRestaurantId(Long restaurantId) throws Exception;
	
	public IngrediantsItem updateStock(Long ingrediantItemId) throws Exception;

}
