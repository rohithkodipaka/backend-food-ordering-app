package com.rohith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	public Cart findByCustomerId(Long userId);
}
