package com.rohith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rohith.model.Food;


public interface FoodRepository extends JpaRepository<Food, Long>{
	
	List<Food> findByRestaurantId(Long restaurantId);
	
	
	@Query("SELECT f FROM Food f WHERE lower(f.name) LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
	List<Food> searchFood(String keyword);

}
