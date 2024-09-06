package com.rohith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohith.model.Food;
import com.rohith.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);

	
}
