package com.rohith.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.config.JwtTokenProvider;
import com.rohith.model.Cart;
import com.rohith.model.USER_ROLE;
import com.rohith.model.User;
import com.rohith.repository.CartRepository;
import com.rohith.repository.UserRepository;
import com.rohith.request.AuthRequest;
import com.rohith.response.AuthResponse;
import com.rohith.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		User isEmailExist = userRepository.findByEmail(user.getEmail());
		if(isEmailExist!=null) {
			throw new Exception("Account already exists with the email");
		}
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFullName(user.getFullName());
		newUser.setRole(user.getRole());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveUser = userRepository.save(newUser);
		Cart cart = new Cart();
		cart.setCustomer(saveUser);
		cartRepository.save(cart);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(user.getRole());
		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody AuthRequest request) throws Exception{
		String userName = request.getUsername();
		String password = request.getPassword();
		
		Authentication authentication = authenticate(userName, password);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		String jwt = jwtTokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Logged In successfully");
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<>(authResponse,HttpStatus.OK);	
	}

	private Authentication authenticate(String userName, String password) throws Exception{
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
		if(userDetails == null)
			throw new BadCredentialsException("Invalid user name");
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
