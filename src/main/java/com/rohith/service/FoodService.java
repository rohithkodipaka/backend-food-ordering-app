package com.rohith.service;

import java.util.List;

import com.rohith.model.Category;
import com.rohith.model.Food;
import com.rohith.model.Restaurant;
import com.rohith.request.CreateFoodReq;

public interface FoodService {
	
	public Food createFood(CreateFoodReq req,Category category,Restaurant restaurant);
	
	public void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId,boolean isVegetarian,boolean isNonVegetarian, boolean isSeasonal,String foodCategory);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailabilityStatus(Long foodId) throws Exception;
	
	

}
