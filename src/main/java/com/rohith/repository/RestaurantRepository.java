package com.rohith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rohith.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

	Restaurant findByOwnerId(Long ownerId);
	
	@Query("SELECT r FROM Restaurant r WHERE lower(r.restaurantName) LIKE lower(concat('%',:query,'%'))"+
										" OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
	List<Restaurant> findBySearchQuery(String query);
}
