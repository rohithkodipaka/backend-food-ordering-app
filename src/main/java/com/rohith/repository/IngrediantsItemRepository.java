package com.rohith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.IngrediantsItem;

public interface IngrediantsItemRepository extends JpaRepository<IngrediantsItem, Long> {

	List<IngrediantsItem> findByRestaurantId(Long restaurantId);

}
