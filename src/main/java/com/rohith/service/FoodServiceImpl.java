package com.rohith.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.model.Category;
import com.rohith.model.Food;
import com.rohith.model.Restaurant;
import com.rohith.repository.FoodRepository;
import com.rohith.request.CreateFoodReq;

@Service
public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private FoodRepository foodRepository;
	
	

	@Override
	public Food createFood(CreateFoodReq req, Category category, Restaurant restaurant) {
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngrediantItems(req.getIngrediants());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());
		
		Food savedFood = foodRepository.save(food);
		restaurant.getFoods().add(savedFood);
		//restaurantRepository.save(restaurant);
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.save(food);
	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVegetarian,
			boolean isSeasonal, String foodCategory) {
		List<Food> food = foodRepository.findByRestaurantId(restaurantId);
		if(isVegetarian) {
			food = filterByVegetarian(food,isVegetarian);	
		}
		if(isNonVegetarian) {
			food = filterByNonVegetarian(food,isNonVegetarian);
		}
		if(isSeasonal) {
			food = filterBySeasonal(food,isSeasonal);
		}
		if(foodCategory!=null && !foodCategory.isEmpty()) {
			food = filterByFoodCategory(food,foodCategory);
		}
		return food;
	}

	private List<Food> filterByFoodCategory(List<Food> food, String foodCategory) {
		List<Food> foodByCategory = food.stream().filter(f->(f.getFoodCategory()!=null)?f.getFoodCategory().getName().equals(foodCategory):false).collect(Collectors.toList());
		return foodByCategory;
	}

	private List<Food> filterBySeasonal(List<Food> food, boolean isSeasonal) {
		List<Food> seasonalFood = food.stream().filter(f-> f.isSeasonal()==isSeasonal).collect(Collectors.toList());
		return seasonalFood;
	}

	private List<Food> filterByNonVegetarian(List<Food> food, boolean isNonVegetarian) {
		List<Food> nonVegFood = food.stream().filter(f->f.isVegetarian()==false).collect(Collectors.toList());
		return nonVegFood;
	}

	private List<Food> filterByVegetarian(List<Food> food, boolean isVegetarian) {
		List<Food> vegetarianFood = food.stream().filter(f->f.isVegetarian() == isVegetarian).collect(Collectors.toList());
		return vegetarianFood;
	}

	@Override
	public List<Food> searchFood(String keyword) {
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> foodOptional = foodRepository.findById(foodId);
		if(foodOptional.isEmpty())
			throw new Exception("Food not exist");
		return foodOptional.get();
	}

	@Override
	public Food updateAvailabilityStatus(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepository.save(food);
		
	}

}
