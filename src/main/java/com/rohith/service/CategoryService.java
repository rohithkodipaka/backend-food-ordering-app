package com.rohith.service;

import java.util.List;

import com.rohith.model.Category;

public interface CategoryService {
	
	public Category createCategory(String name, Long userId) throws Exception;
	
	public List<Category> findCategoryByRestaurantId(Long userId) throws Exception;
	
	public Category findCategoryById(Long id) throws Exception;
	

}
