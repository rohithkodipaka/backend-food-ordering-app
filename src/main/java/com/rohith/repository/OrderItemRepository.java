package com.rohith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
