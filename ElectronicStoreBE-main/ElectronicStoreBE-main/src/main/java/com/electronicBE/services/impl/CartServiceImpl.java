package com.electronicBE.services.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.spi.StrongTypeConditionalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.electronicBE.dtos.AddItemToCartRequest;
import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.CartDto;
import com.electronicBE.entities.Cart;
import com.electronicBE.entities.CartItem;
import com.electronicBE.entities.Product;
import com.electronicBE.entities.User;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.repositories.CartItemRepository;
import com.electronicBE.repositories.CartRepository;
import com.electronicBE.repositories.ProductRepo;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void RemoveCartItem(Long userId, Long cartItemId) {


        // TODO Auto-generated method stub
        CartItem cartItem = this.cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem not found with given id"));

        cartItemRepository.delete(cartItem);


    }

    @Override
    public void ClearCart(Long userId) {
        // TODO Auto-generated method stub

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found given user"));

        cart.getItems().clear();

        this.cartRepository.save(cart);


    }

    @Override
    public CartDto AddToCart(Long userId, AddItemToCartRequest request) {


//		 TODO Auto-generated method stub


        Long productId = request.getProductId();

        int quantity = request.getQuantity();

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id"));


        if (quantity <= 0) {
            throw new BadApiException("Given quantity not valid");
        }

        Cart cart = null;


        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            // TODO: handle exception
            cart = new Cart();

            String date = new SimpleDateFormat("dd/MM/YYYY, hh.mm.ss.aa").format(Calendar.getInstance().getTime());

            cart.setCreatedAt(date);
        }

// if cart is already present


        AtomicReference<Boolean> updated = new AtomicReference<Boolean>(false);

        List<CartItem> items = cart.getItems();
        items.stream().map((item) -> {

            if (item.getProduct().getProductId().equals(productId)) {
//                int newQuantity = item.getQuantity() + quantity;
//                item.setQuantity(item.getQuantity() + quantity);
                item.setQuantity( quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);


            }

            return item;
        }).collect(Collectors.toList());


// create new cart if cart is not present

        if (!updated.get()) {
            CartItem cartItems = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItems);


        }

        cart.setUser(user);


        Cart SavedCart = cartRepository.save(cart);


        return mapper.map(SavedCart, CartDto.class);
    }

    @Override
    public CartDto getCartOfUser(Long userId) {
        // TODO Auto-generated method stub

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found of given user"));


        return mapper.map(cart, CartDto.class);
    }

}
