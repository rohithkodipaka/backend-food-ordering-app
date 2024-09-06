package com.rohith.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.Food;
import com.rohith.model.Restaurant;
import com.rohith.model.User;
import com.rohith.request.CreateFoodReq;
import com.rohith.response.MessageResponse;
import com.rohith.service.FoodService;
import com.rohith.service.RestaurantService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantService restaurantService;

	@PostMapping("")
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodReq req, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restuarant = restaurantService.findRestuarantById(req.getRestaurantId());
		Food food = foodService.createFood(req, req.getCategory(), restuarant);
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		foodService.deleteFood(id);
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setMessage("Food deleted successfully from the restuarant");
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Food> updateFoodStatusAvailability(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Food food = foodService.updateAvailabilityStatus(id);
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
}
