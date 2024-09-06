package com.rohith.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.Category;
import com.rohith.model.Food;
import com.rohith.model.User;
import com.rohith.service.FoodService;
import com.rohith.service.RestaurantService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,@RequestParam String name) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<Food> food = foodService.searchFood(name);
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(@RequestHeader("Authorization") String jwt,@PathVariable Long restaurantId, @RequestParam boolean isVegetarian, @RequestParam boolean isNonVegetarian, @RequestParam boolean isSeasonal, @RequestParam(required=false) String foodCategory) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<Food> food = foodService.getRestaurantFood(restaurantId, isVegetarian, isNonVegetarian, isSeasonal, foodCategory);
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
}
