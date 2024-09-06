package com.rohith.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.model.IngrediantCategory;
import com.rohith.model.IngrediantsItem;
import com.rohith.model.Restaurant;
import com.rohith.repository.IngrediantCategoryRepository;
import com.rohith.repository.IngrediantsItemRepository;

@Service
public class IngrediantServiceImpl implements IngrediantsService{
	
	@Autowired
	private IngrediantCategoryRepository ingrediantCategoryRepository;
	
	@Autowired
	private IngrediantsItemRepository ingrediantsItemRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngrediantCategory createIngrediantCategory(String name, Long restaurantId) throws Exception {
		IngrediantCategory category = new IngrediantCategory();
		Restaurant restuarant = restaurantService.findRestuarantById(restaurantId);
		category.setName(name);
		category.setRestaurant(restuarant);
		return ingrediantCategoryRepository.save(category);
	}

	@Override
	public IngrediantCategory findIngrediantCategoryById(Long ingrediantId) throws Exception {
		Optional<IngrediantCategory> ingrediantCategoryOptional = ingrediantCategoryRepository.findById(ingrediantId);
		if(ingrediantCategoryOptional.isEmpty())
			throw new Exception("Ingrediant Category is not present");
		return ingrediantCategoryOptional.get();
	}

	@Override
	public List<IngrediantCategory> findIngrediantCategoryByRestaurantId(Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestuarantById(restaurantId);
		return ingrediantCategoryRepository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public IngrediantsItem createIngrediantsItem(String ingrediantName, Long restaurantId, Long ingrediantCategoryId)
			throws Exception {
		Restaurant restaurant = restaurantService.findRestuarantById(restaurantId);
		IngrediantCategory ingrediantCategory = findIngrediantCategoryById(ingrediantCategoryId);
		IngrediantsItem ingrediantItem = new IngrediantsItem();
		ingrediantItem.setIngrediantCategory(ingrediantCategory);
		ingrediantItem.setRestaurant(restaurant);
		ingrediantItem.setName(ingrediantName);
		ingrediantCategory.getIngrediantsItem().add(ingrediantItem);
		return ingrediantsItemRepository.save(ingrediantItem);
	}

	@Override
	public List<IngrediantsItem> findIngrediantItemsByRestaurantId(Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestuarantById(restaurantId);
		return ingrediantsItemRepository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public IngrediantsItem updateStock(Long ingrediantItemId) throws Exception {
		Optional<IngrediantsItem> ingrediantsItemOptional = ingrediantsItemRepository.findById(ingrediantItemId);
		if(ingrediantsItemOptional.isEmpty())
			throw new Exception("Ingrediant Item is not present");
		IngrediantsItem ingrediantItem = ingrediantsItemOptional.get();
		ingrediantItem.setInStock(!ingrediantItem.isInStock());
		return ingrediantsItemRepository.save(ingrediantItem);
		
	}

}
