package com.electronicBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronicBE.dtos.AddItemToCartRequest;
import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.CartDto;
import com.electronicBE.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	
	@Autowired
	private CartService cartService;
	
	// add to cart
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> AddToCart(@PathVariable("userId")Long userId,  @RequestBody AddItemToCartRequest addItemToCartRequest){
		
		CartDto Cart = this.cartService.AddToCart(userId, addItemToCartRequest);
		
		return new ResponseEntity<CartDto>(Cart,HttpStatus.OK);
	}
	
	// Remove item from cart
	
	@DeleteMapping("/{userId}/item/{cartItemId}")
	public ResponseEntity<ApiResponseMessage> getRemoveItemFromCart(@PathVariable Long userId,@PathVariable Long cartItemId){
		
		this.cartService.RemoveCartItem(userId, cartItemId);
	
		ApiResponseMessage responseMessage= ApiResponseMessage.builder().message("Item deleted successfully.. !!")
				.success(true)
				.httpStatus(HttpStatus.OK)
				.build();
		
		
		return new ResponseEntity<ApiResponseMessage>(responseMessage,HttpStatus.OK);
	}
	
	// Clear cart
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage>ClearCart(@PathVariable Long userId){
		
		this.cartService.ClearCart(userId);
		
		ApiResponseMessage responseMessage=ApiResponseMessage.builder()
				.message("Cart successfully cleared")
				.success(true)
				.httpStatus(HttpStatus.OK)
				.build();
		
		return new ResponseEntity<ApiResponseMessage>(responseMessage,HttpStatus.OK);
	}
	
	// Get cart from user
	@GetMapping("/user/{userId}")
	public ResponseEntity<CartDto> getCartFromUser(@PathVariable Long userId){
		
		CartDto cartOfUser = this.cartService.getCartOfUser(userId);
		
		return new ResponseEntity<CartDto>(cartOfUser,HttpStatus.OK);
	}
	
	
}
