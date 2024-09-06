package com.rohith.service;

import java.util.List;

import com.rohith.dto.RestaurantDto;
import com.rohith.model.Restaurant;
import com.rohith.model.User;
import com.rohith.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
	
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;
	
	public void deleteRestaurant(Long restaurantId) throws Exception;
	
	public List<Restaurant> getAllRestaurants();
	
	public  List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestuarantById(Long restaurantId) throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
	
	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
	

}
