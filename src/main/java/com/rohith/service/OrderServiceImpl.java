package com.rohith.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.model.Address;
import com.rohith.model.Cart;
import com.rohith.model.CartItem;
import com.rohith.model.Order;
import com.rohith.model.OrderItem;
import com.rohith.model.Restaurant;
import com.rohith.model.User;
import com.rohith.repository.AddressRepository;
import com.rohith.repository.OrderItemRepository;
import com.rohith.repository.OrderRepository;
import com.rohith.repository.UserRepository;
import com.rohith.request.CreateOrderRequest;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	

	@Override
	public Order createOrder(CreateOrderRequest req, User user) throws Exception {
		Address shippingAddress = req.getDeliveryAddress();
		Address savedAddress = addressRepository.save(shippingAddress);
		if(!user.getAddress().contains(savedAddress)) {
			user.getAddress().add(savedAddress);
			userRepository.save(user);
		}
		Restaurant restaurant = restaurantService.findRestuarantById(req.getRestaurantId());
		Order order = new Order();
		order.setCustomer(user);
		order.setDeliveryAddress(savedAddress);
		order.setCreatedAt(new Date());
		order.setOrderStatus("PENDING");
		order.setRestaurant(restaurant);
		Cart cart = cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for(CartItem cartItem:cart.getItem()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			OrderItem savedOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItem);
		}
		Long totalPrice = cartService.calculateCartTotals(cart);
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		Order savedOrder = orderRepository.save(order);
		restaurant.getOrders().add(savedOrder);
		//restaurantRepository.save(restaurant);
		return savedOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order = findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DELIVERY")|| orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING"))
			order.setOrderStatus(orderStatus);
		else
			throw new Exception("Please select valid order Status");
		return orderRepository.save(order); 
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> getUserorder(Long userId) throws Exception {
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
		if(orderStatus!=null)
			orders =orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());	
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> orderOpt = orderRepository.findById(orderId);
		if(orderOpt.isEmpty())
			throw new Exception("Order is not found");
		return orderOpt.get();
	}

}
