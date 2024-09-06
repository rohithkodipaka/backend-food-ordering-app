package com.rohith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
