package com.rohith.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.model.Cart;
import com.rohith.model.CartItem;
import com.rohith.model.User;
import com.rohith.request.AddCartItemRequest;
import com.rohith.request.UpdateCartItemRequest;
import com.rohith.service.CartService;
import com.rohith.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addCartItemToCart(@RequestBody AddCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		CartItem cartItem = cartService.addItemToCart(req, user);
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@PutMapping("/cartItem/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@DeleteMapping("/cartItem/{cartItemId}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.removeItemsFromCart(cartItemId, user);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.clearCart(user.getId());
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.findCartByUserId(user.getId());
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/cart/{cartId}")
	public ResponseEntity<Cart> findCartById(@PathVariable Long cartId, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.findCartById(cartId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

}
