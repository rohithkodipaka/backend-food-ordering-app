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

import com.rohith.dto.RestaurantDto;
import com.rohith.model.Restaurant;
import com.rohith.model.User;
import com.rohith.service.RestaurantService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private UserService userService;

	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurantList = restaurantService.searchRestaurant(keyword);
		return new ResponseEntity<>(restaurantList, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurantList = restaurantService.getAllRestaurants();
		return new ResponseEntity<>(restaurantList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.findRestuarantById(id);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@PutMapping("/{id}/addfavourite")
	public ResponseEntity<RestaurantDto> addRestaurantToFavourites(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		RestaurantDto dto = restaurantService.addToFavourites(id, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	

}
