package com.rohith.service;

import com.rohith.model.Cart;
import com.rohith.model.CartItem;
import com.rohith.model.User;
import com.rohith.request.AddCartItemRequest;

public interface CartService {
	
	public CartItem addItemToCart(AddCartItemRequest req, User user) throws Exception;
	
	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
	
	public Cart removeItemsFromCart(Long cartItemId, User user) throws Exception;
	
	public Long calculateCartTotals(Cart Cart) throws Exception;
	
	public Cart findCartById(Long cartId) throws Exception;
	
	public Cart findCartByUserId(Long userId) throws Exception;
	
	public Cart clearCart(Long userId) throws Exception;	

}
