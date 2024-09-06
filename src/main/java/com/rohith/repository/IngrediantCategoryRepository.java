package com.rohith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.IngrediantCategory;

public interface IngrediantCategoryRepository extends JpaRepository<IngrediantCategory, Long>{
	
	List<IngrediantCategory> findByRestaurantId(Long restaurantId);
	
}
