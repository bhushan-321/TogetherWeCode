package com.electronicBE.services;

import com.electronicBE.dtos.AddItemToCartRequest;
import com.electronicBE.dtos.CartDto;

public interface CartService {
	
	// add to cart
	
	//case1: cart for user is not available: we create the cart and then add item
	
	// case2:if cart is already present so add item in cart 
	
	CartDto AddToCart(Long userId,AddItemToCartRequest addItemToCartRequest);
	

	// RemoveCart
	
	void RemoveCartItem(Long userId, Long cartId);
	
	// Clear cart 
	
	void ClearCart(Long userId);
	
	// Get cart of user
	
	CartDto getCartOfUser(Long userId);
	
	
	
}
