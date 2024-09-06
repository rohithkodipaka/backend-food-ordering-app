package com.rohith.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.config.JwtTokenProvider;
import com.rohith.model.User;
import com.rohith.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private JwtTokenProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		return findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if(user == null) throw new Exception("User not found exception");
		return user;	
	}

}
