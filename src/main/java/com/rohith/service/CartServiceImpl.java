package com.rohith.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.model.Cart;
import com.rohith.model.CartItem;
import com.rohith.model.Food;
import com.rohith.model.User;
import com.rohith.repository.CartItemRepository;
import com.rohith.repository.CartRepository;
import com.rohith.request.AddCartItemRequest;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private FoodService foodService;

	@Override
	public CartItem addItemToCart(AddCartItemRequest req, User user) throws Exception {
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());
		for (CartItem cartItem : cart.getItem()) {
			if (cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity() + req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setFood(food);
		cartItem.setCart(cart);
		cartItem.setQuantity(req.getQuantity());
		cartItem.setIngredients(req.getIngrediants());
		cartItem.setTotalPrice(req.getQuantity() * food.getPrice());
		CartItem saveCartItem = cartItemRepository.save(cartItem);
		cart.getItem().add(saveCartItem);
		// cartRepository.save(cart);
		return saveCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
		if (cartItemOpt.isEmpty())
			throw new Exception("Cart Item is not found");
		CartItem cartItem = cartItemOpt.get();
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
		return cartItemRepository.save(cartItem);
	}

	@Override
	public Cart removeItemsFromCart(Long cartItemId, User user) throws Exception {
		Cart cart = cartRepository.findByCustomerId(user.getId());
		Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
		if (cartItem.isEmpty())
			throw new Exception("Cart Item is not found");
		CartItem item = cartItem.get();
		cart.getItem().remove(item);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total = 0L;
		for (CartItem cartItem : cart.getItem()) {
			total += cartItem.getFood().getPrice() * cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long cartId) throws Exception {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		if (cartOptional.isEmpty())
			throw new Exception("Cart not found with id");
		return cartOptional.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		Cart cart = cartRepository.findByCustomerId(userId);
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		Cart cart = cartRepository.findByCustomerId(userId);
		cart.getItem().clear();
		return cartRepository.save(cart);
	}

}
