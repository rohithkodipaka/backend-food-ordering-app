package com.rohith.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.Category;
import com.rohith.model.User;
import com.rohith.request.CategoryRequest;
import com.rohith.service.CategoryService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminCategoryController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(@RequestHeader("Authorization") String jwt,@RequestBody CategoryRequest req) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Category category = categoryService.createCategory(req.getName(), user.getId());
		return new ResponseEntity<>(category,HttpStatus.CREATED);
	}
	
	@GetMapping("/category/restaurant")
	public ResponseEntity<List<Category>> getCategoryByRestaurantId(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		List<Category> categoryList = categoryService.findCategoryByRestaurantId(user.getId());
		return new ResponseEntity<>(categoryList,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@RequestHeader("Authorization") String jwt,@PathVariable Long categoryId) throws Exception{
		Category category = categoryService.findCategoryById(categoryId);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}

}
