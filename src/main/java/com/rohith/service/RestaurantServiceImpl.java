package com.rohith.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohith.dto.RestaurantDto;
import com.rohith.model.Address;
import com.rohith.model.Restaurant;
import com.rohith.model.User;
import com.rohith.repository.AddressRepository;
import com.rohith.repository.RestaurantRepository;
import com.rohith.repository.UserRepository;
import com.rohith.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address = addressRepository.save(req.getAddress());
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setRestaurantName(req.getRestaurantName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user); 
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		Restaurant restaurant = findRestuarantById(restaurantId);
		if(restaurant.getCuisineType()!=null)
			restaurant.setCuisineType(updatedRestaurant.getCuisineType());
		if(restaurant.getDescription()!=null)
			restaurant.setDescription(updatedRestaurant.getDescription());
		if(restaurant.getRestaurantName()!=null)
			restaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
		return restaurantRepository.save(restaurant); 
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		Restaurant restaurant = findRestuarantById(restaurantId);
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
	    return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestuarantById(Long restaurantId) throws Exception {
		Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
		if(restaurantOptional.isEmpty())
			throw new Exception("Restaurant is not found");
		return restaurantOptional.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
		if(restaurant == null)
			throw new Exception("Restaurant not found with given ownerId"+ userId);
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant = findRestuarantById(restaurantId);
		RestaurantDto dto = new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getRestaurantName());
		dto.setId(restaurantId);
		boolean isFavourite = false;
		List<RestaurantDto> restaurantlist = user.getFavourites();
		for(RestaurantDto restaurantDto : restaurantlist) {
			if(restaurantDto.getId()!=null) {
			if(restaurantDto.getId().equals(restaurantId)) {
				isFavourite = true;
				break;
			}
			}
		}
		if(isFavourite) {
			user.getFavourites().removeIf(restaurantDto->(restaurantDto.getId()!=null)&& restaurantDto.getId().equals(restaurantId));
		}
		else {
			user.getFavourites().add(dto);
		}
		userRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
		Restaurant restaurant = findRestuarantById(restaurantId);
		restaurant.setOpen(!restaurant.isOpen());
		restaurantRepository.save(restaurant);
		return restaurant;
	}

}
