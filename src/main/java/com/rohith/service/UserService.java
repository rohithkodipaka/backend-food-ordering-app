package com.rohith.service;

import com.rohith.model.User;

public interface UserService {
	
	public User findUserByJwtToken(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;

}
