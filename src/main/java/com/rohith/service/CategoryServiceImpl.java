package com.rohith.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.model.Category;
import com.rohith.model.Restaurant;
import com.rohith.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public Category createCategory(String name, Long userId) throws Exception{
		
		Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long userId) throws Exception {
		Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
		List<Category> category = categoryRepository.findByRestaurantId(restaurant.getId());
		return category;
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		if(categoryOptional.isEmpty())
			throw new Exception("Category is not found");
		return categoryOptional.get();
	}

}
