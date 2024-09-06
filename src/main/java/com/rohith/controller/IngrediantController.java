package com.rohith.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.IngrediantCategory;
import com.rohith.model.IngrediantsItem;
import com.rohith.model.User;
import com.rohith.request.IngrediantCategoryRequest;
import com.rohith.request.IngrediantsItemRequest;
import com.rohith.service.IngrediantsService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api/admin/ingrediants")
public class IngrediantController {
	
	@Autowired
	private IngrediantsService ingrediantService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/category")
	public ResponseEntity<IngrediantCategory> createIngrediantCategory(@RequestBody IngrediantCategoryRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		IngrediantCategory ingrediantCategory = ingrediantService.createIngrediantCategory(req.getIngrediantCategoryName(), req.getRestaurantId());
		return new ResponseEntity<>(ingrediantCategory,HttpStatus.CREATED);
	}
	
	@PostMapping("")
	public ResponseEntity<IngrediantsItem> createIngrediantsItem(@RequestBody IngrediantsItemRequest req, @RequestHeader("Authorization")String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		IngrediantsItem ingrediantItem = ingrediantService.createIngrediantsItem(req.getName(), req.getRestaurantId(), req.getCategoryId());
		return new ResponseEntity<>(ingrediantItem,HttpStatus.CREATED);
	}
	
	@PutMapping("/{ingrediantItemId}/stock")
	public ResponseEntity<IngrediantsItem> updateIngrediantStock(@PathVariable Long ingrediantItemId, @RequestHeader("Authorization")String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		IngrediantsItem ingrediantItem = ingrediantService.updateStock(ingrediantItemId);
		return new ResponseEntity<>(ingrediantItem,HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<IngrediantsItem>> getRestaurantIngrediant(@PathVariable Long restaurantId, @RequestHeader("Authorization")String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<IngrediantsItem> ingrediantItem = ingrediantService.findIngrediantItemsByRestaurantId(restaurantId);
		return new ResponseEntity<>(ingrediantItem,HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{restaurantId}/category")
	public ResponseEntity<List<IngrediantCategory>> getRestaurantIngrediantCategory(@PathVariable Long restaurantId, @RequestHeader("Authorization")String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<IngrediantCategory> ingrediantCategory = ingrediantService.findIngrediantCategoryByRestaurantId(restaurantId);
		return new ResponseEntity<>(ingrediantCategory,HttpStatus.OK);
	}
	
	
	

}
